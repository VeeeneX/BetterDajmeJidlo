package cz.jakubfajkus.bdj.delivery;

import cz.jakubfajkus.bdj.delivery.redis.RedisPublisher;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
public class DeliveryNotificationSender {

    @Inject
    RedisPublisher redis;

    public void stateChanged(Delivery delivery) {
        log.info("Sending notification for delivery " + delivery.getId());
        switch (delivery.getState()) {
            case CREATED:
                redis.sendNotification(String.format("Dear %s, your order %s was successfully placed.", delivery.getCustomerName(), delivery.getId()));
                break;
            case ACCEPTED:
                redis.sendNotification(String.format("Dear %s, your order %s was accepted by the restaurant and it's being prepared.", delivery.getCustomerName(), delivery.getId()));
                break;
            case PREPARED:
                redis.sendNotification(String.format("Dear %s, your order %s was successfully finished by the restaurant. It's awaiting to be dispatched.", delivery.getCustomerName(), delivery.getId()));
                break;
            case DISPATCHED:
                redis.sendNotification(String.format("Dear %s, your order %s was dispatched from the restaurant. It's currently on its way to you.", delivery.getCustomerName(), delivery.getId()));
                break;
            case DELIVERED:
                //none
                break;
            case CANCELED:
                redis.sendNotification(String.format("Dear %s, your order %s was canceled. We should contact your shortly.", delivery.getCustomerName(), delivery.getId()));
                break;
        }
    }

}
