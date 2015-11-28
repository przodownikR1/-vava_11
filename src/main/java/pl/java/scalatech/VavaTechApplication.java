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

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.entity.Role;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.repository.RoleRepository;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.service.product.ProductService;


@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = WebSocketAutoConfiguration.class)
public class VavaTechApplication implements CommandLineRunner {


    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(VavaTechApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Role user= roleRepository.save(new Role("USER","only for ordinary user"));
        Role admin= roleRepository.save(new Role("ADMIN","only for special right user"));
        log.info("+++++++++++++   {}",user.getId());

        User one = userRepository.save(User.builder().firstName("slawek").login("przodownik")
                .password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").enabled(true).build());
        User two = userRepository.save(User.builder().firstName("vava").login("vava")
                .password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").enabled(true)
                .build());



        one.setRoles(Lists.newArrayList(user,admin));
        two.setRoles(Lists.newArrayList(user));
        userRepository.save(one);
        userRepository.save(two);


        List<Product> products = newArrayList(Product.builder().name("olowek").price(BigDecimal.valueOf(123)).quantity(1).owner(one).build(),
                Product.builder().name("zarowka").price(BigDecimal.valueOf(23)).quantity(1).enable(true).owner(two).build(),
                Product.builder().name("samochod").price(BigDecimal.valueOf(12333)).quantity(13).enable(true).owner(one).build(),
                Product.builder().name("laptop").price(BigDecimal.valueOf(5523)).quantity(31).owner(two).build());

        for (Product product : products) {
            Product loaded = productService.save(product);
            log.info("{}", loaded);
        }

    }
}
