package com.cafepos.demo;
import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.OrderIds;

import java.util.Optional;

public final class Week2Demo {

public static void main(String[] args) {

Catalog catalog = new InMemoryCatalog(); // Create catalog object 
catalog.add(new SimpleProduct("P-ESP", "Espresso",Money.of(2.50))); // Add espresso ($2.50)
catalog.add(new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50))); // Add chocolate cookie ($3.50)

Order order = new Order(OrderIds.next()); // Create order object 
order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2)); // Add products to order 
order.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));

int taxPct = 10; 
// Print order details
System.out.println("Order #" + order.id());
System.out.println("Items: " + order.items().size());
System.out.println("Subtotal: " + order.subtotal());
System.out.println("Tax (" + taxPct + "%): " +
order.taxAtPercent(taxPct));
System.out.println("Total: " +
order.totalWithTax(taxPct));
}
}