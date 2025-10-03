package com.cafepos.observer;
import com.cafepos.domain.Order;

public final class DeliveryDesk implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
// TODO: on "ready" -> print "[Delivery] Order #<id> is ready for delivery"
    }
}
