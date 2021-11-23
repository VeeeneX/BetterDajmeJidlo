package cz.muni.jakubfajkus.pv217.bdj;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.RedisClientName;
import io.quarkus.runtime.StartupEvent;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class RedisSubscriber {

    @Inject
    Vertx vertx;

    private static final Logger log = LoggerFactory.getLogger(RedisSubscriber.class);

    @ConfigProperty(name = "quarkus.redis.notifications.hosts")
    private String connectionString;

    public void subscribeNotifications(@Observes StartupEvent ev) {
        log.info("The app is starting... in subscribeNotifications");
        RedisOptions options = new RedisOptions();
        options.setEndpoints(List.of(connectionString));

        Redis.createClient(vertx, options)
                .connect()
                .onSuccess(conn -> {
                    conn
                            .send(Request.cmd(Command.SUBSCRIBE).arg("notifications"))
                            .onSuccess(response -> log.info("Subscribed to notifications " + response))
                            .onFailure(response -> log.info("Failed to subscribe " + response));

                    conn.handler(message -> {
                        if (message.size() == 3 && message.get(1).toString().equals("notifications")) {
                            log.info("Notification received: " + message.get(2));
                        }
                    });
                })
                .onFailure(response -> log.error("Failed to connect to redis: " + response));
    }
}
