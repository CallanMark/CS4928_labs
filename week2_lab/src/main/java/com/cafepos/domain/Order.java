public final class Order {
private final long id;
private final List<LineItem> items = new ArrayList<>();
public Order(long id) { this.id = id; }

// NOTE : Add error handling for all functions 
public void addItem(LineItem li) { 
    items.add(li);
 }
public Money subtotal() {
return items.stream().map(LineItem::lineTotal).reduce(Money.zero(), Money::add);
}
public Money taxAtPercent(int percent) 
{ 
    if (percent < 0 || percent > 100) {
        throw new IllegalArgumentException("Tax percentage must be between 0 and 100");
    }
return subtotal().multiply(percent);
 }
public Money totalWithTax(int percent) { 

return subtotal().add(taxAtPercent(percent));
 }
