package cz.jakubfajkus.bdj.delivery.health;

import cz.jakubfajkus.bdj.delivery.Delivery;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Readiness;

import javax.enterprise.context.ApplicationScoped;

@Readiness
@ApplicationScoped
public class ReadinessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() { // should we bother the application with requests?
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Database connection health check");

        try {
            Delivery.findFirstByState(Delivery.State.CREATED).subscribe().with(item -> responseBuilder.up(), failure -> responseBuilder.down());
        } catch (Throwable e) {
            responseBuilder.down();
        }

        return responseBuilder.build();
    }
}