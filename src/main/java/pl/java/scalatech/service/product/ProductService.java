package pl.java.scalatech.service.product;

import java.util.List;

import pl.java.scalatech.entity.Product;
import pl.java.scalatech.entity.User;

public interface ProductService {

    List<Product> getAll();

    List<Product> getAllByUser(User user);

    Product save(Product product);

    void testEx()  throws IllegalAccessException;

    void testEx1();

    Product findOne(Long id);

    void delete(Product product);


}
