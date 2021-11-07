package cz.jakubfajkus.bdj.delivery.redis;

import io.quarkus.redis.client.RedisClientName;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.redis.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RedisPublisher {

    @Inject
    @RedisClientName("notifications")
    ReactiveRedisClient redis;

    private static final Logger log = LoggerFactory.getLogger(RedisPublisher.class);


    public void sendNotification(String notification) {
        redis.publish("notifications", notification)
                .subscribe()
                .with(
                        item -> log.info("Notification successful: " + notification),
                        failure -> log.error("Failed to send notification: " + notification)
                );

    }
}
