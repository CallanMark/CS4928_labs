package cafepos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cafepos.payment.PaymentStrategy;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.domain.LineItem;
import com.cafepos.domain.Order;
import com.cafepos.common.Money;

public class PaymentTest{

@Test void payment_strategy_called() {
    var p = new SimpleProduct("001", "Coffee", Money.of(5)); // Add new product with id 001 , Name Coffee, Price 5
    var order = new Order(42); // Add new order with id 42
    order.addItem(new LineItem(p, 1)); // Add order object (which contains the product object )
    final boolean[] called = {false}; 
    PaymentStrategy fake = o -> called[0] = true;
    order.pay(fake);
    assertTrue(called[0], "[payment_strategy_called]Payment strategy should be called");

    

}

@Test void payment_strategy_called_with_multiple_products() {

    var order = new Order(42); // Add new order with id 42
    var p2 = new SimpleProduct("002", "Tea", Money.of(3)); // Add new product with id 002 , Name Tea, Price 3
    order.addItem(new LineItem(p2, 2)); // Add order object (which contains the product object )
    final boolean[] called2 = {false}; 
    PaymentStrategy fake2 = o -> called2[0] = true;
    order.pay(fake2);
    assertTrue(called2[0], "[payment_strategy_called_with_multiple_products] Payment strategy should be called for second product");

    var p3 = new SimpleProduct("003", "Juice", Money.of(4)); // Add new product with id 003 , Name Juice, Price 4
    order.addItem(new LineItem(p3, 3)); // Add order object (which contains the product object )
    final boolean[] called3 = {false}; 
    PaymentStrategy fake3 = o -> called3[0] = true;
    order.pay(fake3);
    assertTrue(called3[0], "[payment_strategy_called_with_multiple_products] Payment strategy should be called for third product");
}

@Test void payment_strategy_called_with_card_payment() {
    var order = new Order(42); // Add new order with id 42
    var p2 = new SimpleProduct("002", "Tea", Money.of(3)); // Add new product with id 002 , Name Tea, Price 3
    order.addItem(new LineItem(p2, 2)); // Add order object (which contains the product object )
    final boolean[] cardPaymentCalled = {false}; 
    PaymentStrategy cardPaymentStrategy = o -> cardPaymentCalled[0] = true;
    order.pay(cardPaymentStrategy);
    assertTrue(cardPaymentCalled[0], "[payment_strategy_called_with_card_payment] Card payment strategy should be called for the product");
}

@Test void payment_strategy_called_with_cash_payment() {
    var order = new Order(42); // Add new order with id 42
    var p2 = new SimpleProduct("002", "Tea", Money.of(3)); // Add new product with id 002 , Name Tea, Price 3
    order.addItem(new LineItem(p2, 2)); // Add order object (which contains the product object )
    final boolean[] cashPaymentCalled = {false}; 
    PaymentStrategy cashPaymentStrategy = o -> cashPaymentCalled[0] = true;
    order.pay(cashPaymentStrategy);
    assertTrue(cashPaymentCalled[0], "[payment_strategy_called_with_cash_payment] Cash payment strategy should be called for the product");
}
}