package com.cafepos.smells;
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;

public class OrderManagerGod {
    public static int TAX_PERCENT = 10; //
    public static String LAST_DISCOUNT_CODE = null;
    public static String process(String recipe, int qty, String
            paymentType, String discountCode, boolean printReceipt) //God class, long method - does the creating, pricing, discounts and receipts all in one.

    {
        ProductFactory factory = new ProductFactory();
        Product product = factory.create(recipe); // god class, long method - creating object mixed into pricing
        Money unitPrice;
        try {
            var priced = product instanceof com.cafepos.decorator.Priced
                    p ? p.price() : product.basePrice();
            unitPrice = priced;
        } catch (Exception e) {
            unitPrice = product.basePrice();
        }
        if (qty <= 0) qty = 1;
        Money subtotal = unitPrice.multiply(qty);
        Money discount = Money.zero();
        if (discountCode != null) { //Primitive obsession, discountCode is a free-form string
            if (discountCode.equalsIgnoreCase("LOYAL5")) //Feature Envy/Shotgun Surgery - discount rule embedded here
            {
                discount = Money.of(subtotal.asBigDecimal()
                        .multiply(java.math.BigDecimal.valueOf(5)) // Primitive obsession
                        .divide(java.math.BigDecimal.valueOf(100))); // primitive obsession
            } else if (discountCode.equalsIgnoreCase("COUPON1")) {
                discount = Money.of(1.00); // primitive obsession
            } else if (discountCode.equalsIgnoreCase("NONE")) {
                discount = Money.zero();
            } else {
                discount = Money.zero();
            }
            LAST_DISCOUNT_CODE = discountCode; // Global/Static State
        }
        Money discounted =
                Money.of(subtotal.asBigDecimal().subtract(discount.asBigDecimal()));
        if (discounted.asBigDecimal().signum() < 0) discounted =
                Money.zero();
        var tax = Money.of(discounted.asBigDecimal()
                .multiply(java.math.BigDecimal.valueOf(TAX_PERCENT))
                .divide(java.math.BigDecimal.valueOf(100))); //duplicated logic - Money and BigDecimal maths is repeated
        var total = discounted.add(tax);
        if (paymentType != null) {
            if (paymentType.equalsIgnoreCase("CASH")) { // Primitive Obsession/Shotgun Surgery (routing embedded)
                System.out.println("[Cash] Customer paid " + total + " EUR");
            } else if (paymentType.equalsIgnoreCase("CARD")) { // Primitive Obsession/Shotgun Surgery (routing embedded)
                System.out.println("[Card] Customer paid " + total + " EUR with card ****1234");
            } else if (paymentType.equalsIgnoreCase("WALLET")) {// Primitive Obsession/Shotgun Surgery (routing embedded)
                System.out.println("[Wallet] Customer paid " + total + " EUR via wallet user-wallet-789");
            } else {
                System.out.println("[UnknownPayment] " + total);
            }
        }
        StringBuilder receipt = new StringBuilder();
        receipt.append("Order (").append(recipe).append(") x").append(qty).append("\n");
                receipt.append("Subtotal: ").append(subtotal).append("\n");
        if (discount.asBigDecimal().signum() > 0) {
            receipt.append("Discount: - ").append(discount).append("\n");
        }
        receipt.append("Tax (").append(TAX_PERCENT).append("%): ").append(tax).append("\n");
                receipt.append("Total: ").append(total);
        String out = receipt.toString();
        if (printReceipt) { //primitive obsession
            System.out.println(out); //god class/long method
        }

        return out;
    }
}
