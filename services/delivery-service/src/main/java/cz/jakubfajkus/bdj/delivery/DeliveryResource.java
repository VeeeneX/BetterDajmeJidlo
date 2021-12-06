package cz.jakubfajkus.bdj.delivery;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Produces("application/json")
@Consumes("application/json")
@Path("/delivery")
@ApplicationScoped
public class DeliveryResource {
    @Inject
    DeliveryNotificationSender notificationSender;

    @GET
    public Uni<List<Delivery>> list() {
        return Delivery.listAll(Sort.by(Delivery.Fields.created).descending());
    }

    @GET
    @Path("/{id}")
    public Uni<Delivery> get(@PathParam("id") UUID id) {
        return Delivery.findById(id);
    }

    @POST
    @Counted(value = "ordersCreated", description = "How many orders have been created")
    @Timed
    public Uni<Response> create(@Valid Delivery delivery) {
        Uni<Delivery> deliveryUni = Panache.withTransaction(delivery::persist);

        return deliveryUni.onItem().transform(inserted -> {
            notificationSender.stateChanged(inserted);
            return Response.created(URI.create("/delivery/" + inserted.getId())).build();
        });
    }

    @PATCH
    @Path("/{id}/state")
    public Uni<Response> changeState(@PathParam("id") UUID id, Delivery.State newState) {
        return Panache
                .withTransaction(() -> Delivery.<Delivery>findById(id)
                        .onItem().ifNotNull().invoke(entity -> entity.setState(newState))
                        .onItem().ifNotNull().invoke(entity -> notificationSender.stateChanged(entity))
                )
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }


    @POST
    @Path("/advance")
    public Uni<Response> advanceRandomOrder() {
        var state = Delivery.State.values()[new Random().nextInt(Delivery.State.values().length)];

        if (state.getNextState() == null) {
            return Uni.createFrom().item(Response.ok("No next state " + state).build());
        }

        return Panache
                .withTransaction(() -> Delivery.findFirstByState(state)
                        .onItem().ifNotNull().invoke(entity -> entity.setState(state.getNextState()))
                        .onItem().ifNotNull().invoke(entity -> notificationSender.stateChanged(entity))
                )
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok("No delivery found for state " + state).status(NOT_FOUND)::build);
    }

}