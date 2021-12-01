package cz.jakubfajkus.bdj.delivery;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Table(name = "delivery")
@Getter
@Setter
@Entity
@Cacheable
@FieldNameConstants
public class Delivery extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(nullable = false)
    @CreationTimestamp
    private Instant created;

    @Column(nullable = false)
    @NotBlank
    private String restaurantId;

    @Column(nullable = false)
    @NotBlank
    private String orderContent;

    @Column(nullable = false)
    @NotBlank
    private String customerId;

    @Column(nullable = false)
    @NotBlank
    private String customerName;

    @Column(nullable = false)
    @NotBlank
    private String street;

    @Column(nullable = false)
    @NotBlank
    private String city;

    @Column(nullable = false)
    @NotBlank
    private String zip;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private State state = State.CREATED;


    public static Uni<Delivery> findFirstByState(State state) {
        return find(Fields.state, state).firstResult();
    }


    @Getter
    @AllArgsConstructor
    public enum State {
        CANCELED(null),

        DELIVERED(null),
        DISPATCHED(DELIVERED),
        PREPARED(DISPATCHED),
        ACCEPTED(PREPARED),
        CREATED(ACCEPTED);

        private State nextState;

    }


}


