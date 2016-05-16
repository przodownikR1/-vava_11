package pl.java.scalatech.service.product.impl;

import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private EntityManager em;
    
   final private @NonNull ProductRepository productRepository;
    Random r = new Random();

    @Override
    @Trace
    @SneakyThrows
    @Cacheable(value="product")
    public List<Product> getAll() {
        Thread.sleep(r.nextInt(1000));
        return productRepository.findAll();
    }

    @Transactional
    @Override
    @Trace

    @CachePut(value="product",key="#product.id")
    public Product save(Product product) {
        return productRepository.save(product);
    }

    
    @Transactional
    @Override
    @Trace
    @CachePut(value="product",key="#product.id")
    public Product saveEm(Product product) {
        Product loaded = em.find(Product.class, product.getId());
        loaded.setName(product.getName());
        loaded.setPrice(product.getPrice());
        loaded.setQuantity(product.getQuantity());
        return loaded;
    }
    
    @CatchException(sendEmail=true)
    @Override
    public void testEx() throws IllegalAccessException  {
       throw new IllegalAccessException("hej cos jest nie tak !!!");

    }
    @CatchException(sendEmail=true)
    @Override
    public void testEx1()  {
       throw new IllegalArgumentException("hej cos jest nie tak runtime !!!");

    }

    @Override
    public Product findOne(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    @Transactional
    public void delete(Product product) {
        productRepository.delete(product);

    }

  /*  @Override
    public List<Product> getAllByUser(User user) {
        return productRepository.findByOwner(user);
    }*/

}
