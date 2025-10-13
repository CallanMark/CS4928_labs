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
import com.cafepos.decorator.*;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.WalletPayment;
import com.cafepos.payment.CashPayment;
import com.cafepos.demo.entryPointTesting;
import java.util.Scanner;

public final class EntryPoint { 
    public static void main(String[] args) {
        // Create Objects 
        Catalog catalog = new InMemoryCatalog(); 
        Order order = new Order(OrderIds.next()); 
        ProductFactory factory = new ProductFactory();
        Scanner scanner = new Scanner(System.in);

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
         /*
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

        boolean mainMenuActive = true;
        while (mainMenuActive){
      
         System.out.println("[Main Menu] Welcome top the CS4928 Cafe , Please select which operation you wish to perform");
         System.out.println("1. Create/Add a new product to the catalog");
         System.out.println("2. Add a new item to the order");
         System.out.println("3. Remove an item from the order");
         System.out.println("4. Complete the order and print the receipt");
         System.out.println("5. Exit");
        /*  
        switch (scanner.nextInt()) {
            case 1:
                entryPointLogic.addProduct(scanner);
                break;
            case 2:
                entryPointLogic.addItem(scanner);
                break; 
            case 3:
                entryPointLogic.removeItem(scanner);
                break;
            case 4:
                entryPointLogic.completeTransaction(scanner);
                break;
            case 5:
                System.out.println("Exiting program...");
                scanner.close();
                mainMenuActive = false;
                break;
         }
         System.out.println("Returning to main menu...");
        }
        */
        
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // clear newline
         switch (choice) {
            case 1:
            System.out.println("Please enter the product name e.g Latte , Espresso , Cappuccino "); 
            String name = scanner.nextLine();
            if (name.length() > 10){   
                throw new IllegalArgumentException("Product name is too long , please enter a shorter name");
            }
            System.out.println("Please enter the product price ");
            double price = scanner.nextDouble(); 
            if (price <= 0){
                throw new IllegalArgumentException("Invalid price , price must be greater than 0");
                
            }
            String id = "P-"+name.substring(0, 3).toUpperCase();
            catalog.add(new SimpleProduct(id, name, Money.of(price)));
            System.out.println("Product added successfully to catalog with id :" + id + " , returning to main menu");   
            break;
            case 2:   
            // User flow 
            System.out.println("Please enter the product ID e.g P-LAT, P-ESP, P-CAP");
            String productId = scanner.nextLine();
            if (!productId.startsWith("P-") || productId.length() != 5){
                throw new IllegalArgumentException("Invalid product id , please enter a valid product id e.g P-ESP");
            }
            // NOTE : This is where we check if the product exists in the catalog
            if (!catalog.findById(productId).isPresent()){
                throw new IllegalArgumentException("Product not found in catalog , please enter a valid product id");
            }
            Product product = catalog.findById(productId).orElseThrow();
            System.out.println("Please enter the quantity");
            scanner.nextLine(); // clear newline
            int userQuantity = scanner.nextInt();
            if (userQuantity <= 0){ // User input checks can me improved throughout 
                throw new IllegalArgumentException("Invalid quantity , Quantity must be greater than 0");
            }
            System.out.println("Product id is valid , adding item to order");
    
            System.out.println("Would you like a large " + product.name() + "? (y/n)");
            String largeChoice = scanner.nextLine().toLowerCase();
            if (largeChoice.equals("y")){
                product = new SizeLarge(product);
            } else if (largeChoice.equals("n")){
                throw new IllegalArgumentException("Invalid choice , please enter y or n");
            } 
    
            System.out.println("Would you like an extra shot of espresso in " + product.name() + "? (y/n)");
            String shotChoice = scanner.nextLine().toLowerCase();
            if (shotChoice.equals("y")){
                product = new ExtraShot(product);
            } else if (!shotChoice.equals("n")){
                throw new IllegalArgumentException("Invalid choice , please enter y or n");        } else {
            }
    
            
            System.out.println("Would you like an oat milk in " + product.name() + "? (y/n)");
            String oatChoice = scanner.nextLine().toLowerCase();
            if (oatChoice.equals("y")){
                product = new OatMilk(product);
            } else if (oatChoice.equals("n")){
                throw new IllegalArgumentException("Invalid choice , please enter y or n");
            }
    
            catalog.add(new SimpleProduct(productId, productId, Money.of(product.price().toBigDecimal())));
            
            order.addItem(new LineItem(product, userQuantity));
            // New intiations , only iniating this when it is needed , Don't know if this is the most efficent way 
            order.register(new KitchenDisplay());
            order.register(new DeliveryDesk());
            order.register(new CustomerNotifier());
            order.addItem(new LineItem(catalog.findById(productId).orElseThrow(), userQuantity));
            System.out.println("Item added successfully , returning to main menu");

            case 3:
                 String itemId = scanner.nextLine();
                if (!itemId.startsWith("P-") || itemId.length() != 5){
                    System.out.println("Invalid item id , please enter a valid item id e.g P-ESP");            
                }
                else if (!catalog.findById(itemId).isPresent() ){ // || TODO: Implement some sort of check for  !order.items().contains(itemId)
                    System.out.println("Item not found in order , please enter a valid item id");
                    System.out.println("Current Order items: " + order.items());
                }
                else {
                    System.out.println("Item found in order , removing item");
                    Product itemForRemoval = catalog.findById(itemId).orElseThrow();
                    LineItem lineItemForRemoval = new LineItem(itemForRemoval, 1);
                    //order.removeItem(itemId); //TODO: Implement this method
                    order.removeItem(lineItemForRemoval); // Takes param of type product 
                    System.out.println("Item removed successfully , returning to main menu");    
                }
                break;

            case 4:
            System.out.println("Please select the payment method");
            System.out.println("1. Cash");
            System.out.println("2. Card");
            System.out.println("3. Wallet");
         

            int paymentMethodChoice = scanner.nextInt();
            if (paymentMethodChoice <= 0 || paymentMethodChoice > 3){
                System.out.println("Invalid payment method , please enter a valid payment method");
            }

            String userPaymentMethod = "" ; // Initalise userPaymentMethod to an empty string , doing this make the code more readable imo 
            if (paymentMethodChoice == 1) {userPaymentMethod = "Cash";}
            else if (paymentMethodChoice == 2) {userPaymentMethod = "Card";}
            else if (paymentMethodChoice == 3) {userPaymentMethod = "Wallet";}

            if (userPaymentMethod.equals("Cash")){
                System.out.println("Cash selected , please enter the amount of cash to tender");
                double cashAmount = scanner.nextDouble();
                double change = cashAmount - order.totalWithTax(10).toBigDecimal().doubleValue(); // TODO: Check this correct ? , Add a test case for this 
                order.pay(new CashPayment());
                System.out.println("Cash payment successful , returning to main menu");
                System.out.println("Change tendered: " + change);

            }
            else if (userPaymentMethod.equals("Card")){
                System.out.println("Please enter your card number ");
                String cardNumber = scanner.nextLine();
                order.pay(new CardPayment(cardNumber));
                System.out.println("Card payment successful , returning to main menu");
            }
            else if (userPaymentMethod.equals("Wallet")){
                System.out.println("Please enter your wallet id");
                String walletId = scanner.nextLine();
                order.pay(new WalletPayment(walletId));
                System.out.println("Wallet payment successful , returning to main menu");
            }
            else {
                System.out.println("Invalid payment method , please enter a valid payment method");
            }

            order.markReady();
            System.out.println("Order marked as ready , returning to main menu");
              
            case 5:
                System.out.println("Exiting program...");
                mainMenuActive = false;
                scanner.close();
                break;
         }
        }  
    }
}