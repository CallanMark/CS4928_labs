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
import com.cafepos.catalog.Product;
import com.cafepos.decorator.*;
import com.cafepos.payment.CardPayment;
import com.cafepos.payment.WalletPayment;
import com.cafepos.pricing.LoyaltyPercentDiscount;
import com.cafepos.pricing.DiscountPolicy;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.FixedRateTaxPolicy;
import com.cafepos.pricing.NoDiscount;

import java.util.Scanner;

public final class EntryPoint { 
    public static void main(String[] args) {
        // Visit this link to see provided demo code from previous weeks : https://github.com/spider-mmp/cafepos-demo/tree/main/src/main/java/com/cafepos/demo
        // Create Objects 
        Catalog catalog = new InMemoryCatalog(); 
        Order order = new Order(OrderIds.next()); 
        order.register(new KitchenDisplay());
        order.register(new DeliveryDesk());
        order.register(new CustomerNotifier());
        Scanner scanner = new Scanner(System.in);

        boolean mainMenuActive = true;
        while (mainMenuActive){
         System.out.println("[Main Menu] Welcome to the CS4928 Cafe , Please select which operation you wish to perform");
         System.out.println("1. Create/Add a new product to the catalog");
         System.out.println("2. Add a new item to the order");
         System.out.println("3. Remove an item from the order");
         System.out.println("4. Complete the order and print the receipt");
         System.out.println("5. Exit");
        
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // clear newline
         // Need a way to navigate back to the main menu from any case
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
            String productId = scanner.nextLine().toUpperCase();
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
            scanner.nextLine();
            if (userQuantity <= 0){ // User input checks can me improved throughout 
                throw new IllegalArgumentException("Invalid quantity , Quantity must be greater than 0");
            }
            System.out.println("Product id is valid , adding item to order");
    
            System.out.println("Would you like a large " + product.name() + "? (y/n)");
            String largeChoice = scanner.nextLine().toLowerCase();
            if (largeChoice.equals("y")){
                product = new SizeLarge(product);
            } else if (!largeChoice.equals("n")){
                throw new IllegalArgumentException("Invalid choice , please enter y or n");
            } 
    
            System.out.println("Would you like an extra shot of espresso in " + product.name() + "? (y/n)");
            String shotChoice = scanner.nextLine().toLowerCase();
            if (shotChoice.equals("y")){
                product = new ExtraShot(product);
            } else if (!shotChoice.equals("n")){
                throw new IllegalArgumentException("Invalid choice , please enter y or n");     
               } 
    
            
            System.out.println("Would you like an oat milk in " + product.name() + "? (y/n)");
            String oatChoice = scanner.nextLine().toLowerCase();
            if (oatChoice.equals("y")){
                product = new OatMilk(product);
            } else if (!oatChoice.equals("n")){
                throw new IllegalArgumentException("Invalid choice , please enter y or n");
            }
    
            order.addItem(new LineItem(product, userQuantity));            
            System.out.println("Item added successfully , returning to main menu");
            break;
            case 3:
                 String itemId = scanner.nextLine();
                if (!itemId.startsWith("P-") || itemId.length() != 5){
                    System.out.println("Invalid item id , please enter a valid item id e.g P-ESP");            
                }
                else if (!catalog.findById(itemId).isPresent() ){ 
                    System.out.println("Item not found in order , please enter a valid item id");
                    System.out.println("Current Order items: " + order.items());
                }
                else {
                    System.out.println("Item found in order , removing item");
                    Product itemForRemoval = catalog.findById(itemId).orElseThrow();
                    LineItem lineItemForRemoval = new LineItem(itemForRemoval, 1);
                    order.removeItem(lineItemForRemoval); // Takes param of type product 
                    System.out.println("Item removed successfully , returning to main menu");    
                }
                break;

            case 4:
            System.out.println("Your current total is " + order.totalWithTax(10));
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
                // NOTE : We need to display the total amount of the order and the tax amount so customer can't cheat by not paying the correct amount 
                System.out.println("Cash selected , please enter the amount of cash to tender");
                double cashAmount = scanner.nextDouble();
                Money cashAmountToPay = Money.of(cashAmount);
                Money totalAmountToPay = order.totalWithTax(10); 
                if (cashAmountToPay.compareTo(totalAmountToPay) < 0){
                    System.out.println("Insufficient cash tendered , please enter a valid amount");
                    break;
                }
                Money changedTendered = cashAmountToPay.subtract(totalAmountToPay);
                order.pay(new CashPayment());
                System.out.println("Change tendered: " + changedTendered);
                System.out.println("Cash payment successful , returning to main menu");
                
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
            
    
            DiscountPolicy discountPolicy = new NoDiscount();
            System.out.println("Do you have a discount code? Enter it here if so (1 = LOYAL5) , (2 = COUPON1), (3 = n )");
            int discountCode = scanner.nextInt();
            scanner.nextLine(); // clear newline
            if (discountCode == 1 ) {
                discountPolicy = new LoyaltyPercentDiscount(5);
            } else if (discountCode  == 2 ) {
                discountPolicy = new LoyaltyPercentDiscount(1);
                
            } else if (discountCode == 3 ) {
                System.out.println("No discount code applied ... proceeding to checkout");
            } else {
                System.out.println("Unknown discount code, ignoring.");
            }

            PricingService pricingService = new PricingService(discountPolicy, new FixedRateTaxPolicy(10));
            PricingService.PricingResult pricing = pricingService.price(order.subtotal());
                        
            System.out.println("Order marked as ready , printing receipt...");
            System.out.println("*************************** Receipt ***************************");
            System.out.println("Order #" + order.id());
            for (LineItem li : order.items()) {
                System.out.println(" - " + li.product().name() + " x" + li.quantity() + " = " + li.lineTotal());
            }
            System.out.println("Subtotal: " + pricing.subtotal());
            System.out.println("Tax (10%): " + pricing.tax());
            System.out.println("Discount: " + pricing.discount());
            System.out.println("Total: " + pricing.total());
            order.markReady();
            break ; 
              
            case 5:
                System.out.println("Exiting program...");
                mainMenuActive = false;
                scanner.close();
                break;
         }
        }  
    }
}