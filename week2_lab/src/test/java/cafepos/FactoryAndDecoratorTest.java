package cafepos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import com.cafepos.decorator.ExtraShot;
import com.cafepos.decorator.OatMilk;
import com.cafepos.decorator.SizeLarge;
import com.cafepos.decorator.Priced;
import com.cafepos.factory.ProductFactory;
import com.cafepos.catalog.Product;

public class FactoryAndDecoratorTest {

    @Test 
    void decorator_single_addon() {
        /*
         * NOTE : Error on calulcating price , 
         */
        Product espresso = new SimpleProduct("P-ESP", "Espresso",
        Money.of(2.50));
        Product withShot = new ExtraShot(espresso);
        assertEquals("Espresso + Extra Shot", withShot.name());
        // if using Priced interface:
        System.out.println("With extra shot: " + ((Priced) withShot).price());
        assertEquals(Money.of(3.30), ((Priced) withShot).price());
       
        }
    
    @Test 
    void decorator_stacks() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso",Money.of(2.50));
        Product decorated = new SizeLarge(new OatMilk(new ExtraShot(espresso)));
        assertEquals("Espresso + Extra Shot + Oat Milk (Large)", decorated.name());
        assertEquals(Money.of(4.50), ((Priced) decorated).price());
        }

    @Test 
    void factory_parses_recipe() {
        ProductFactory f = new ProductFactory();
        Product p = f.create("ESP+SHOT+OAT");
        assertTrue(p.name().contains("Espresso") &&
        p.name().contains("Oat Milk"));
        }
        
    @Test 
    void order_uses_decorated_price() {
        Product espresso = new SimpleProduct("P-ESP", "Espresso",
        Money.of(2.50));
        Product withShot = new ExtraShot(espresso); // 3.30
        Order o = new Order(1);
        o.addItem(new LineItem(withShot, 2));
        assertEquals(Money.of(6.60), o.subtotal());
        }
}