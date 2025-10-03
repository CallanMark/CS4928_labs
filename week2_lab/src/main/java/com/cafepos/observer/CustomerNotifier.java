package com.cafepos.observer;

import com.cafepos.domain.Order;

public class CustomerNotifier implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
// TODO: print "[Customer] Dear customer, your Order #<id> has been updated: <event>."
    }
}
