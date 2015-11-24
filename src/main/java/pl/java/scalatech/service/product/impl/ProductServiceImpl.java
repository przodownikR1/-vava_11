package pl.java.scalatech.service.product.impl;

import java.util.List;
import java.util.Random;

import org.hibernate.type.TrueFalseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import pl.java.scalatech.annotation.CatchException;
import pl.java.scalatech.annotation.Trace;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.repository.ProductRepository;
import pl.java.scalatech.service.product.ProductService;


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
    @CatchException(sendEmail=true)
    @Override
    public void testEx() throws IllegalAccessException  {
       throw new IllegalAccessException("hej cos jest nie tak !!!");
        
    }
    @CatchException(sendEmail=true)
    @Override
    public void testEx1()  {
       throw new IllegalArgumentException("hej cos jest nie tak !!!");
        
    }

}
