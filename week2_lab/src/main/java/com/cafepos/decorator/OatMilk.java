package com.cafepos.decorator;
import com.cafepos.catalog.Product;
import com.cafepos.common.Money;

public final class OatMilk extends ProductDecorator implements Priced {
    private static final Money SURCHARGE = Money.of(0.50);
    public OatMilk(Product base) { 
        super(base);
     }
    @Override public String name() { 
        return base.name() + " + Oat Milk"; 
    }
    public Money price() { 
        Money basePrice = (base instanceof Priced p)
        ? p.price()          // if the wrapped product is another decorator or priced product
        : base.basePrice(); 
        return basePrice.add(SURCHARGE); 
        
    }
}
