package com.cafepos.app.events;

public sealed interface OrderEvent permits OrderCreated, OrderPaid { }

public record OrderCreated(long orderId) implements OrderEvent { }
public record OrderPaid(long orderId) implements OrderEvent { }