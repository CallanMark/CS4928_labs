package com.cafepos.payment;
import com.cafepos.domain.Order;
public final class CardPayment implements PaymentStrategy {
private final String cardNumber;
public CardPayment(String cardNumber) {
    if (cardNumber == null) {
        System.out.print("Card Number cannot be null"); }

    String digits = cardNumber.replaceAll("\\D", "");
    if(digits.length() != 16) {
        System.out.println("Card Number must be 16 digits"); }

    this.cardNumber = digits;
}

@Override
public void pay(Order order) {
    String masked = maskCardDetails(cardNumber);
    System.out.println("[Card] Customer paid " + order.totalWithTax(10) + " EUR with card " + masked);
}

private static String maskCardDetails(String digits) {
    //digits guaranteed to be 16
    int keep = 4;
    int maskCount = digits.length() - keep;
    StringBuilder sb = new StringBuilder(digits.length());
    for (int i = 0; i < maskCount; i++) sb.append('*');
    sb.append(digits.substring(digits.length() - keep));
    return sb.toString();
    }
}







