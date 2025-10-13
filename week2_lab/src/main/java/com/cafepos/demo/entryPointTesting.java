package com.cafepos.demo;

import java.util.Scanner;
import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.Product;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.decorator.OatMilk;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.OrderIds;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.WalletPayment;
import com.cafepos.observer.KitchenDisplay;
import com.cafepos.observer.DeliveryDesk;
import com.cafepos.observer.CustomerNotifier;


public class entryPointTesting {
    /*
     * Explanation of the functions: 
     *  addProduct() : Adds a product to the catalog
     *  addItem() : Adds an item to the order
     *  removeItem() : Removes an item from the order
     *  completeTransaction() : Completes the transaction
     * 
     */
 

    
    public static void addProduct(Scanner scanner) {
        Catalog catalog = new InMemoryCatalog();
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
    }

    public static void addItem(Scanner scanner) {
        // Init objects 
        Catalog catalog = new InMemoryCatalog();
        Order order = new Order(OrderIds.next());

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
        int userQuantity = scanner.nextInt();
        if (userQuantity <= 0){ // User input checks can me improved throughout 
            throw new IllegalArgumentException("Invalid quantity , Quantity must be greater than 0");
        }
        System.out.println("Product id is valid , adding item to order");

        System.out.println("Would like a large " + productId + "? (y/n)");
        String largeChoice = scanner.nextLine().toLowerCase();
        if (largeChoice.equals("y")){
            product = new SizeLarge(product);
        } else if (largeChoice.equals("n")){
            throw new IllegalArgumentException("Invalid choice , please enter y or n");
        } 

        System.out.println("Would like an extra shot of espresso in " + productId + "? (y/n)");
        String shotChoice = scanner.nextLine().toLowerCase();
        if (shotChoice.equals("y")){
            product = new ExtraShot(product);
        } else if (!shotChoice.equals("n")){
            throw new IllegalArgumentException("Invalid choice , please enter y or n");        } else {
        }

        System.out.println("Would like an oat milk in " + productId + "? (y/n)");
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
    }

    public static void removeItem(Scanner scanner) {
                Catalog catalog = new InMemoryCatalog();
                Order order = new Order(OrderIds.next());
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
        }

    public static void completeTransaction(Scanner scanner) { 
            // Init objects 
            // NOTE : This is where to implement the decarator pattern for the receipt

            Order order = new Order(OrderIds.next());
    
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
                else if (paymentMethodChoice == 3) {userPaymentMethod = "Wallet";
}

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
        }
        
        public static void testFunctions(Scanner scanner) { 
            // Init objects 
            Catalog catalog = new InMemoryCatalog();
            Order order = new Order(OrderIds.next());

            // User flow 
            System.out.println("Please enter the product name e.g P-LAT, P-ESP, P-CAP");
            String name = scanner.nextLine();
            System.out.println("Product Name :" + name);
            System.out.println("Please enter the product price ");
            double price = scanner.nextDouble();
            
            String id = "P-"+name.substring(0, 3).toUpperCase();
            catalog.add(new SimpleProduct(id, name, Money.of(price)));
            System.out.println("Product added to catalog with id :" + id );


            System.out.println("ADD ITEM TO CATALOG");
            order.addItem(new LineItem(catalog.findById(id).orElseThrow(), 1));
            
    
            }    
        }
