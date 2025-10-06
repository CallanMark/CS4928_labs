package com.cafepos.demo;
import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.OrderIds;
import com.cafepos.payment.CashPayment;
import com.cafepos.observer.DeliveryDesk;
import com.cafepos.observer.KitchenDisplay;
import com.cafepos.observer.CustomerNotifier;

public final class EntryPoint { 
    public static void main(String[] args) {
        // Init Objects
        Catalog catalog = new InMemoryCatalog(); // Create catalog object 
        Order order = new Order(OrderIds.next()); // Create order object 

        // ----------------------- Week 2 Demo 
        /* 
        System.out.println("Week 2 Demo ");
        catalog.add(new SimpleProduct("P-ESP", "Espresso",Money.of(2.50))); // Add espresso ($2.50)
        catalog.add(new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50))); // Add chocolate cookie ($3.50)

        order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2)); // Add 2 espresso 
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
        */

        // -----------------------  Week 3 Demo
        /*
        System.out.println("Week 3 Lab Demo" );
        catalog.add(new SimpleProduct("P-ESP", "Espresso",
                Money.of(2.50)));
        catalog.add(new SimpleProduct("P-CCK", "Chocolate Cookie", Money.of(3.50)));
        // Cash payment
                Order order1 = new Order(OrderIds.next());
        order1.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
                order1.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));
                        System.out.println("Order #" + order1.id() + " Total: " +
                                order1.totalWithTax(10));
        order1.pay(new CashPayment());
        // Card payment
        Order order2 = new Order(OrderIds.next());
        order2.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 2));
                order2.addItem(new LineItem(catalog.findById("P-CCK").orElseThrow(), 1));
                        System.out.println("Order #" + order2.id() + " Total: " +
                                order2.totalWithTax(10));
        order2.pay(new CardPayment("1234567812341234"));
        */ 

        // ----------------------- Week 4 Demo 
        System.out.println("Week 4 demo") ; 
        catalog.add(new SimpleProduct("P-ESP", "Espresso",
                Money.of(2.50)));

        order.register(new KitchenDisplay());
        order.register(new DeliveryDesk());
        order.register(new CustomerNotifier());
        order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 1));
        order.pay(new CashPayment());
        order.markReady();
    }
}