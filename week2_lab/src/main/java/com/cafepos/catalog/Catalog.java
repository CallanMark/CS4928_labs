package com.cafepos.catalog;
import java.util.Optional;

// This is the Catalog interface. It defines the methods that any class implementing Catalog must have.
public interface Catalog {

    // This method is used to add a product to the catalog.
    void add(Product p);

    // This method is used to find a product in the catalog by its id. 
    // It returns an Optional that will contain the Product if it is found, and will be empty if the Product is not found.
    Optional<Product> findById(String id);
}
