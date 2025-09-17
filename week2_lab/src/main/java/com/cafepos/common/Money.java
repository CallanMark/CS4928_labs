import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Money implements Comparable<Money> {

// NOTE : We need to always use BigDecimal for money calculations to avoid floating point calculation issues
private final BigDecimal amount;
public static Money of(double value) { ... }
public static Money zero() { ... }

private Money(BigDecimal a) {
if (a == null) throw new IllegalArgumentException("amount
required");
this.amount = a.setScale(2, RoundingMode.HALF_UP);
}
public Money add(Money other) { ... }
public Money multiply(int qty) { ... }
// equals, hashCode, toString, etc.
}