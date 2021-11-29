package cz.jakubfajkus.bdj.delivery.health;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;

@Liveness
@ApplicationScoped  
public class LivenessCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() { //should be restarted?
        HealthCheckResponseBuilder responseBuilder = HealthCheckResponse.named("Randomized check");

        if ((new Random()).nextBoolean()) {
            responseBuilder.down();
        } else {
            responseBuilder.up();
        }


        return responseBuilder.build();
    }
}