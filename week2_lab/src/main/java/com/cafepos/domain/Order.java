package com.cafepos.domain;
import com.cafepos.common.Money;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
public final class Order {
private final long id;
private final List<LineItem> items = new ArrayList<>();
public Order(long id) { this.id = id; }

// NOTE : Add error handling for all functions 
public void addItem(LineItem li) { 
    items.add(li);
 }

public Money taxAtPercent(int percent) { 
    if (percent < 0 || percent > 100) {
        throw new IllegalArgumentException("Tax percentage must be between 0 and 100");
    }
    Money subtotalValue = subtotal();
    System.out.println("Passed in subtotal of :" + subtotalValue);
    Money tax = subtotalValue.multiply(percent);
    System.out.println("Calculated tax of :" + tax);
    return tax;
 }

public Money totalWithTax(int percent) { 
return subtotal().add(taxAtPercent(percent));
 }

public long id() { return id; }
public List<LineItem> items() { return items; }
}