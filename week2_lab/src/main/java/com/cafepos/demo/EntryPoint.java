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
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;
import com.cafepos.decorator.*;;

public final class EntryPoint { 
    public static void main(String[] args) {
        // Create Objects 
        Catalog catalog = new InMemoryCatalog(); 
        Order order = new Order(OrderIds.next()); 
        ProductFactory factory = new ProductFactory();

        // ----------------------- Week 2 Demo 
        /* 
        System.out.println("*************************** Week 2 Demo ***************************");
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
        System.out.println("*************************** Week 3 Demo ***************************");
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

        /* 
        System.out.println("*************************** Week 4 Demo ***************************");
        catalog.add(new SimpleProduct("P-ESP", "Espresso",
                Money.of(2.50)));

        order.register(new KitchenDisplay());
        order.register(new DeliveryDesk());
        order.register(new CustomerNotifier());
        order.addItem(new LineItem(catalog.findById("P-ESP").orElseThrow(), 1));
        order.pay(new CashPayment());
        order.markReady();
        */

        // ----------------------- Week 5 Demo
        /*
         * Latte = 2.50 , Large Latte = 3.20
         * Extra Shot = 0.80 , Oat Milk = 0.50 , Syrup = 0.40 , Large = 0.70
         * Espresso = 2.50 , Large Espresso = 3.20
         */

        System.out.println("*************************** Week 5 Demo ***************************");
        Product p1 = factory.create("ESP+SHOT+OAT"); // Espresso+ Extra Shot + Oat
        Product p2 = factory.create("LAT+L"); // Large Latte
        order.addItem(new LineItem(p1, 1));
        order.addItem(new LineItem(p2, 2));
        System.out.println("Order #" + order.id());
        for (LineItem li : order.items()) {
        System.out.println(" - " + li.product().name() + " x"
        + li.quantity() + " = " + li.lineTotal());
        }
        System.out.println("Subtotal: " + order.subtotal());
        System.out.println("Tax (10%): " +
        order.taxAtPercent(10));
        System.out.println("Total: " + order.totalWithTax(10));
    
        System.out.println("*************************** Testing ***************************");
        // Example usage
        Product espresso = new SimpleProduct("P-ESP", "Espresso", Money.of(2.50));
        Product decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));
        // decorated.name() => "Espresso + Extra Shot + Oat Milk (Large)" NOTE : Working 
        // decorated.price() => 2.50 + 0.80 + 0.50 + 0.70 = 4.5
        System.out.println("Decorated name: " + decorated.name());
        System.out.println("Decorated price: " + decorated.price());
        
        System.out.println("*************************** Testing 2 ***************************");
        Product espresso2 = new SimpleProduct("P-ESP", "Espresso",Money.of(2.50));
        Product decorated2 = new SizeLarge(new OatMilk(new ExtraShot(espresso2)));
        System.out.println("Espresso + Extra Shot + Oat Milk (Large)"+  decorated2.name());
        System.out.println(decorated2.price());
        
       // Currently we add the large surcharrge (2.50 + 0.70 = 3.20) , but we need to add the large surcharrge to the extra shot and oat milk , 

        
        /*
        Desired output : 
        Order #2001
        - Espresso + Extra Shot + Oat Milk x1 = 3.80
        - Latte (Large) x2 = 7.80
        Subtotal: 11.60
        Tax (10%): 1.16
        Total: 12.76
         */
    }
}