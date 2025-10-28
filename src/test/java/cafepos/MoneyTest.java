package cafepos;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.cafepos.common.Money;

public class MoneyTest {

    @Test
    void addition_should_sum_values() {
        assertEquals(Money.of(5.00), Money.of(2.00).add(Money.of(3.00)));
    }

    @Test
    void multiplication_should_scale_value() {
        assertEquals(Money.of(6.00), Money.of(2.00).multiply(3));
    }

    @Test
    void zero_should_be_immutable_identity() {
        assertEquals(Money.of(2.00), Money.zero().add(Money.of(2.00)));
    }
}