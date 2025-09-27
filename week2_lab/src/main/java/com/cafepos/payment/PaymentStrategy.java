package com.cafepos.payment

public interface PaymentStrategy(Order order){
void pay(Order order);
}
