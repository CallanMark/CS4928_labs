package com.cafepos.domain;
import com.cafepos.common.Money;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import com.cafepos.payment.PaymentStrategy;
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

}