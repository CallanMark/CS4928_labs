package com.cafepos.common;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;


public final class Money implements Comparable<Money> {

// NOTE : We need to always use BigDecimal for money calculations to avoid floating point calculation issues
// NOTE : Check java version by running java -version in the terminal , I'm using 23.0.2 , if you run into issues it could be due to use using different java version
private final BigDecimal amount;

public static Money of(double value) {
BigDecimal raw = BigDecimal.valueOf(value);
return new Money(raw);
}

public static Money of(BigDecimal value) {
return new Money(value);
}

public static Money zero() {
return new Money(BigDecimal.ZERO);
}

private Money(BigDecimal a) {
if (a == null) throw new IllegalArgumentException("amount required");
BigDecimal scaled = a.setScale(2, RoundingMode.HALF_UP);
if (scaled.compareTo(BigDecimal.ZERO) < 0) {
throw new IllegalArgumentException("amount cannot be negative");
}
this.amount = scaled;
}

public Money add(Money other) { // NOTE : HERE is casuing error 
Objects.requireNonNull(other, "other");
BigDecimal newAmount = this.amount.add(other.amount);
return new Money(newAmount);
}

public Money subtract(Money other) { // NOTE: We were  not asked for this method , added it as we will probably need it at some stage 
Objects.requireNonNull(other, "other");
BigDecimal newAmount = this.amount.subtract(other.amount);
return new Money(newAmount);
}

public Money multiply(int factor) {
BigDecimal newAmount = this.amount.multiply(BigDecimal.valueOf(factor));
return new Money(newAmount);
}

public Money multiply(BigDecimal percent) {
BigDecimal newAmount = this.amount.multiply(percent);
return new Money(newAmount);
}

public Money multiply(double factor) {
BigDecimal newAmount = this.amount.multiply(BigDecimal.valueOf(factor));
return new Money(newAmount);
}

public BigDecimal toBigDecimal() {
return amount;
}

@Override
public int compareTo(Money o) {
Objects.requireNonNull(o, "o");
return this.amount.compareTo(o.amount);
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof Money)) return false;
Money money = (Money) o;
return amount.compareTo(money.amount) == 0;
}

@Override
public int hashCode() {
return amount.stripTrailingZeros().hashCode();
}

@Override
public String toString() {
return amount.toPlainString();
}
}