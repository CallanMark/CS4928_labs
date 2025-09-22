package com.cafepos.catalog;
import com.cafepos.common.Money;

public final class SimpleProduct implements Product {

private final String id;
private final String name;
private final Money basePrice;
// Constructor
public SimpleProduct(String id, String name, Money basePrice){ 
    this.id = id;
    this.name = name;
    this.basePrice = basePrice;
 }
// Getters
@Override public String id() { return id; }
@Override public String name() { return name; }
@Override public Money basePrice() { return basePrice; }
}