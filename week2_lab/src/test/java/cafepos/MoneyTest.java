package cafepos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
// NOTE: Ensure to change this class to correct money tests before submisson 
public class MoneyTest { 
@Test void money_test() {

 SimpleProduct p1 = new SimpleProduct("A", "A", Money.of(2.50));
 SimpleProduct p2 = new SimpleProduct("B", "B", Money.of(3.50));
 Order o = new Order(1);
 o.addItem(new LineItem(p1, 2));
 o.addItem(new LineItem(p2, 1));
 assertEquals(Money.of(8.50), o.subtotal());
 assertEquals(Money.of(0.85), o.taxAtPercent(10));
 assertEquals(Money.of(9.35), o.totalWithTax(10));

}
}
