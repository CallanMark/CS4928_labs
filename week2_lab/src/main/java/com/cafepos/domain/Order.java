package com.cafepos.domain;
import com.cafepos.common.Money;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.observer.OrderObserver;
import java.util.Arrays;
public final class Order {
private final long id;
private final List<LineItem> items = new ArrayList<>();

public Order(long id) { 
    this.id = id; 
}

public void addItem(LineItem li) { 
    items.add(li);
    if (items.contains(li)) { // Check that state has changed before notifying observers
        notifyObservers("itemAdded");
    }
 }


public Money taxAtPercent(int percent) { 
    BigDecimal percentDecimal = BigDecimal.valueOf(percent).movePointLeft(2);
    if (percent < 0 || percent > 100) {
        throw new IllegalArgumentException("Tax percentage must be between 0 and 100");
    }
    Money subtotalValue = subtotal();
    Money tax = subtotalValue.multiply(percentDecimal);
    return tax;
 }

public Money totalWithTax(int percent) { 
return subtotal().add(taxAtPercent(percent));
 }

public Money subtotal() {
return items.stream().map(LineItem::lineTotal).reduce(Money.zero(), Money::add);
}


public long id() {
     return id;
     }

public List<LineItem> items() {
     return items;
     }

public void pay(PaymentStrategy strategy) {
if (strategy == null) throw new
    IllegalArgumentException("strategy required");
    strategy.pay(this);
    notifyObservers("paid"); 
    }

    // 1) Maintain subscriptions
private final List<OrderObserver> observers = new ArrayList<>();
// Array containing event types for comparison
String[] eventTypes = new String[] { "itemAdded", "paid", "ready" };


public void register(OrderObserver o) {
    if (o == null) { throw new 
    IllegalArgumentException("[register] o must not be null ");
    }
    observers.add(o);
    }
    public void unregister(OrderObserver o) {
    if (o == null) { throw new 
    IllegalArgumentException("[unregister] o must not be null ");
    }
    if (!observers.contains(o)) { throw new 
    IllegalArgumentException("[unregister] Unable to remove o : " + o + "from oberservers as it does not exist ");
    }
    observers.remove(o);
    }
    
    // 2) Publish events
    private void notifyObservers(String eventType) {
        if (!Arrays.asList(eventTypes).contains(eventType)) { throw new 
        IllegalArgumentException("[notifyObservers] Invalid event type: " + eventType + "must be itemAdded, paid, or ready");
        }
        for (OrderObserver o : observers) {
            o.updated(this, eventType);
        }
    }

    public void markReady() {
        notifyObservers("ready");
    }
   // Added this method to get the item name from the order , itemId is the index of the item in the order
    public String getItemName(int itemId) {
        return items.get(itemId).product().name();
    }

    public Integer getItemQuantity(int itemId) {
        return items.get(itemId).quantity();
    }

}