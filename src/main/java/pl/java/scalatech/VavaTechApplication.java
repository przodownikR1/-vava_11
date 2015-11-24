package pl.java.scalatech;

import static com.google.common.collect.Lists.newArrayList;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.repository.ProductRepository;
import pl.java.scalatech.service.product.ProductService;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = WebSocketAutoConfiguration.class)
public class VavaTechApplication implements CommandLineRunner {

    List<Product> products = newArrayList(Product.builder().name("olowek").price(BigDecimal.valueOf(123)).quantity(1).build(),
            Product.builder().name("zarowka").price(BigDecimal.valueOf(23)).quantity(1).build(),
            Product.builder().name("samochod").price(BigDecimal.valueOf(12333)).quantity(13).build(),
            Product.builder().name("laptop").price(BigDecimal.valueOf(5523)).quantity(31).build());

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(VavaTechApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (Product product : products) {
            Product loaded = productService.save(product);
            log.info("{}", loaded);
        }

    }
}
