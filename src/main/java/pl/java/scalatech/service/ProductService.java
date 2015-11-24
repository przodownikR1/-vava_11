package pl.java.scalatech.service;

import java.util.List;

import pl.java.scalatech.entity.Product;

public interface ProductService {

    List<Product> getAll();
    
    Product save(Product product);
    
    
    void testEx()  throws IllegalAccessException;
    
}
