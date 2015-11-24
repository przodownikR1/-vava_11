package pl.java.scalatech.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.java.scalatech.annotation.Trace;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.repository.ProductRepository;
import pl.java.scalatech.service.ProductService;


@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //@NonNull
public class ProductServiceImpl  implements ProductService{

   final private @NonNull ProductRepository productRepository;
    Random r = new Random();
    
    @Override
    @Trace
    @SneakyThrows
    public List<Product> getAll() {
        Thread.sleep(r.nextInt(1000));
        return productRepository.findAll();
    }

    @Transactional
    @Override
    @Trace
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void testEx() throws IllegalAccessException  {
       throw new IllegalAccessException("hej cos jest nie tak !!!");
        
    }

}
