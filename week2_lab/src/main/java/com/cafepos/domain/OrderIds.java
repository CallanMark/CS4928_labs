package com.cafepos.domain;
import java.util.concurrent.atomic.AtomicLong;

public final class OrderIds {
private static final AtomicLong COUNTER = new AtomicLong(0);
private OrderIds() {}
public static long next() { return COUNTER.incrementAndGet(); }
}

