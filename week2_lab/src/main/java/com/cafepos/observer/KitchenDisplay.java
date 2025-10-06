package com.cafepos.observer;
import com.cafepos.domain.Order;


public final class KitchenDisplay implements OrderObserver {
    @Override
    public void updated(Order order, String eventType) {
        if (eventType.equals("itemAdded")) {
            String itemName = order.getItemName(((int)order.id()) -1 ); // Decrement by 1 as we referencing an index 
            System.out.println("[Kitchen] Order #" + order.id() + ": " + order.getItemQuantity((int)order.id()-1)+"x "+itemName + " added");
        } else if (eventType.equals("paid")) {
            System.out.println("[Kitchen] Order #" + order.id() + ": payment received");
        }
    }
}
