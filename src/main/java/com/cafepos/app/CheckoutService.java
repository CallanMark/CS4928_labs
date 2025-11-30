package com.cafepos.app;
import com.cafepos.common.Money;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;
import com.cafepos.pricing.PricingService;
import com.cafepos.pricing.ReceiptPrinter;


public class CheckoutService {
    private final OrderRepository orders;
    private final PricingService pricing;

    public CheckoutService(OrderRepository orders, PricingService pricing) {
    this.orders = orders;
    this.pricing = pricing;
    }
    
     /** Returns receipt string — no printing. */
    public String checkout(long orderId, int taxPercent) {
        Order order = orders.findById(orderId).orElseThrow();
        var pr = pricing.price(order.subtotal());
        return new ReceiptFormatter().format(orderId, order.items(), pr, taxPercent);
    }
    
}