
package cafepos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.cafepos.menu.Menu;
import com.cafepos.menu.MenuItem;
import com.cafepos.menu.MenuComponent;
import com.cafepos.common.Money;
import com.cafepos.state.OrderFSM;
import java.util.List;  
public class Week9Tests {    
@Test void depth_first_iteration_collects_all_nodes() {

Menu root = new Menu("ROOT");
Menu a = new Menu("A");
Menu b = new Menu("B");

root.add(a); root.add(b);
a.add(new MenuItem("x", Money.of(1.0), true));
b.add(new MenuItem("y", Money.of(2.0), false));

List<String> names =root.allItems().stream().map(MenuComponent::name).toList();
assertTrue(names.contains("x"));
assertTrue(names.contains("y"));
}

@Test void order_fsm_happy_path() {
OrderFSM fsm = new OrderFSM();
assertEquals("NEW", fsm.status());
fsm.pay();
assertEquals("PREPARING", fsm.status());
fsm.markReady();
assertEquals("READY", fsm.status());
fsm.deliver();
assertEquals("DELIVERED", fsm.status());
    }
    
@Test
void order_fsm_illegal_transition_stays_same_state() {
OrderFSM fsm = new OrderFSM();
fsm.prepare(); // should print invalid
assertEquals("NEW", fsm.status());
    }    

@Test
void vegetarian_items_filter_correctly() {
Menu root = new Menu("ROOT");
        root.add(new MenuItem("Soup", Money.of(3.0), false));
        root.add(new MenuItem("Salad", Money.of(2.5), true));

        var veg = root.vegetarianItems();
        assertEquals(1, veg.size());
        assertEquals("Salad", veg.get(0).name());
    }

}

