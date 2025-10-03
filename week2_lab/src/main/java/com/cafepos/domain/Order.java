package com.cafepos.domain;
import com.cafepos.common.Money;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.observer.OrderObserver;
public final class Order {
private final long id;
private final List<LineItem> items = new ArrayList<>();
public Order(long id) { this.id = id; }

public void addItem(LineItem li) { 
    items.add(li);
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


public long id() { return id; }
public List<LineItem> items() { return items; }


    public void pay(PaymentStrategy strategy) {
        if (strategy == null) throw new
                IllegalArgumentException("strategy required");
        strategy.pay(this);
    }

    // 1) Maintain subscriptions
    private final List<OrderObserver> observers = new
            ArrayList<>();
    public void register(OrderObserver o) {
// TODO: add null check and add the observer
    }
    public void unregister(OrderObserver o) {
// TODO: remove the observer if present
    }
    // 2) Publish events
    private void notifyObservers(String eventType) {
// TODO: iterate observers and call updated(this, eventType);
    }

    // 3) Hook notifications into existing behaviors
    //@Override
   // public void addItem(LineItem li) {
// TODO: call super/add to items and then
       // notifyObservers("itemAdded");
    //}


    //@Override
    //public void pay(PaymentStrategy strategy) {
// TODO: delegate to strategy as before, then
        //notifyObservers("paid")
    //}
    public void markReady() {
// TODO: just publish notifyObservers("ready")
    }
}