package cafepos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cafepos.catalog.SimpleProduct;
import com.cafepos.common.Money;
import com.cafepos.domain.Order;
import com.cafepos.domain.LineItem;
import java.util.List;
import java.util.ArrayList;
import com.cafepos.payment.CashPayment;

public class OrderObserverTest {

    @Test void observers_notified_on_item_add() {
        var p = new SimpleProduct("A", "A", Money.of(2));
        var o = new Order(1);
        o.addItem(new LineItem(p, 1)); // baseline
        List<String> events = new ArrayList<>();
        o.register((order, evt) -> events.add(evt));
        o.addItem(new LineItem(p, 1));
        assertTrue(events.contains("itemAdded"));
    }

    @Test void observers_notified_on_payment() {
        var p = new SimpleProduct("A", "A", Money.of(2));
        var o = new Order(1);
        o.addItem(new LineItem(p, 1));
        List<String> events = new ArrayList<>();
        o.register((order, evt) -> events.add(evt));
        o.pay(new CashPayment());
        assertTrue(events.contains("paid"));
    }

    @Test void multiple_observers_receive_ready_event() {
        // Arrange
        var order = new Order(2);
        List<String> events1 = new ArrayList<>();
        List<String> events2 = new ArrayList<>();
    
        // Two fake observers
        order.register((o, evt) -> events1.add(evt));
        order.register((o, evt) -> events2.add(evt));
    
        // Act
        order.markReady();
    
        // Assert
        assertTrue(events1.contains("ready"),
                "First observer should receive 'ready' event");
        assertTrue(events2.contains("ready"),
                "Second observer should receive 'ready' event");
    }
    
}