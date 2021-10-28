package cz.jakubfajkus.bdj.delivery;

import cz.jakubfajkus.bdj.delivery.redis.RedisPublisher;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class DeliveryNotificationSender {

    @Inject
    RedisPublisher redis;

    public void stateChanged(Delivery delivery) {
        switch (delivery.getState()) {
            case CREATED:
                //none
            case ACCEPTED:
                redis.sendNotification(String.format("Dear %s, your order was accepted by the restaurant and it's being prepared.", delivery.getCustomerName()));
                break;
            case PREPARED:
                redis.sendNotification(String.format("Dear %s, your order was successfully finished by the restaurant. It's awaiting to be dispatched.", delivery.getCustomerName()));
                break;
            case DISPATCHED:
                redis.sendNotification(String.format("Dear %s, your order was dispatched from the restaurant. It's currently on its way to you.", delivery.getCustomerName()));
                break;
            case DELIVERED:
                //none
                break;
            case CANCELED:
                redis.sendNotification(String.format("Dear %s, your order was canceled. We should contact your shortly.", delivery.getCustomerName()));
                break;
        }
    }

}
