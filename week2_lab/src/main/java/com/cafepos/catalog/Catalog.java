package com.cafepos.catalog;
import java.util.Optional;

// This is the Catalog interface. It defines the methods that any class implementing Catalog must have.
public interface Catalog {

   
    void add(Product p);

   Optional<Product> findById(String id);
}
