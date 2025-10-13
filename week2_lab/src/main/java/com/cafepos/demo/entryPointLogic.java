package com.cafepos.demo;

import java.util.Scanner;
import com.cafepos.catalog.Catalog;
import com.cafepos.catalog.InMemoryCatalog;
import com.cafepos.catalog.Product;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.OrderIds;
import com.cafepos.payment.CashPayment;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.WalletPayment;
import com.cafepos.observer.KitchenDisplay;
import com.cafepos.observer.DeliveryDesk;
import com.cafepos.observer.CustomerNotifier;


public class entryPointLogic {
    /*
     * Explanation of the functions: 
     *  addProduct() : Adds a product to the catalog
     *  addItem() : Adds an item to the order
     *  removeItem() : Removes an item from the order
     *  completeTransaction() : Completes the transaction
     * 
     */
 

    
    public void addProduct(Catalog catalog, Product product) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the product name e.g P-LAT, P-ESP, P-CAP"); 
        String name = scanner.nextLine();
        if (name.length() > 10){   
            scanner.close();
            throw new IllegalArgumentException("Product name is too long , please enter a shorter name");
        }
        System.out.println("Please enter the product price ");
        double price = scanner.nextDouble(); 
        if (price <= 0){
            scanner.close();
            throw new IllegalArgumentException("Invalid price , price must be greater than 0");
            
        }
        
        String id = "P-"+name.substring(0, 2).toUpperCase();
        catalog.add(new SimpleProduct(id, name, Money.of(price)));
        System.out.println("Product added successfully , returning to main menu");
        catalog.add(product);
        scanner.close();
      
    }


    public void addItem() {
        // Init objects 
        Scanner scanner = new Scanner(System.in);
        Catalog catalog = new InMemoryCatalog();
        Order order = new Order(OrderIds.next());

        // User flow 
        System.out.println("Please enter the product name e.g P-LAT, P-ESP, P-CAP");
        String productId = scanner.nextLine();
        if (!productId.startsWith("P-") || productId.length() != 5){
            scanner.close();
            throw new IllegalArgumentException("Invalid product id , please enter a valid product id e.g P-ESP");
        }
        Product product = catalog.findById(productId).orElseThrow();
        System.out.println("Please enter the quantity");
        int userQuantity = scanner.nextInt();
        if (userQuantity <= 0){ // User input checks can me improved throughout 
            scanner.close();
            throw new IllegalArgumentException("Invalid quantity , Quantity must be greater than 0");
        }
        System.out.println("Product id is valid , adding item to order");

        System.out.println("Please enter the price ");
        double price = scanner.nextDouble();
        if (price <= 0){
           scanner.close();
           throw new IllegalArgumentException("Invalid price , price must be greater than 0");
        }
        
        // Add the product to the catalog
        catalog.add(new SimpleProduct(productId, productId, Money.of(price)));

        order.addItem(new LineItem(product, userQuantity));
        // New intiations , only iniating this when it is needed , Don't know if this is the most efficent way 
        order.register(new KitchenDisplay());
        order.register(new DeliveryDesk());
        order.register(new CustomerNotifier());
        order.addItem(new LineItem(catalog.findById(productId).orElseThrow(), userQuantity));
        System.out.println("Item added successfully , returning to main menu");
        scanner.close();

    }

    public void removeItem() {
                Scanner scanner = new Scanner(System.in);
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
                    //order.removeItem(itemId); //TODO: Implement this method
                    System.out.println("Item removed successfully , returning to main menu");
                    
                }
                scanner.close();
        }

        public void completeTransaction() { 
            // Init objects 

            Scanner scanner = new Scanner(System.in);
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
                if (paymentMethodChoice == 1) {
                userPaymentMethod = "Cash";
                }
                else if (paymentMethodChoice == 2) {
                userPaymentMethod = "Card";
                }
                else if (paymentMethodChoice == 3) {
                userPaymentMethod = "Wallet";
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
                scanner.close();
        }    
    }
