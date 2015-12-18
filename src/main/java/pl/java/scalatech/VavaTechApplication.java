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
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.entity.Role;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.repository.ProductRepository;
import pl.java.scalatech.repository.RoleRepository;
import pl.java.scalatech.repository.UserRepository;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = WebSocketAutoConfiguration.class)
public class VavaTechApplication implements EmbeddedServletContainerCustomizer , CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;


    public static void main(String[] args) {
        SpringApplication.run(VavaTechApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {

        Role user = new Role("USER", "only for ordinary user");
        Role admin =new Role("ADMIN", "only for special right user");
        log.info("+++++++++++++   {}", user.getId());
        user = roleRepository.save(user);
        admin = roleRepository.save(admin); 
        User one = userRepository.save(User.builder().firstName("slawek").login("przodownik").password("vava").enabled(true).build());
        User two = userRepository.save(User.builder().firstName("vava").login("vava").password("vava").enabled(true).build());

        one.setRoles(Lists.newArrayList(user, admin));
        two.setRoles(Lists.newArrayList(user));
        User oneLoaded = userRepository.save(one);
        User twoLoaded = userRepository.save(two);
        
        List<Product> products = newArrayList(Product.builder().name("olowek").price(BigDecimal.valueOf(123)).quantity(1).owner(one).build(),
                Product.builder().name("zarowka").price(BigDecimal.valueOf(23)).quantity(1).enable(true).owner(two).build(),
                Product.builder().name("samochod").price(BigDecimal.valueOf(12333)).quantity(13).enable(true).owner(one).build(),
                Product.builder().name("laptop").price(BigDecimal.valueOf(5523)).quantity(31).owner(two).build());

        for (Product product : products) {
            Product loaded = productRepository.save(product);
            log.info("productId : {},  product.owner.id {}", product.getId(), loaded.getOwner().getId());
        }

        log.info("+++ prods for user 1 : {}  ", productRepository.findByOwner(oneLoaded).size());

    }

    @Override
    public void customize(final ConfigurableEmbeddedServletContainer container)
    {
        ((TomcatEmbeddedServletContainerFactory) container).addContextCustomizers(context ->{


            context.setSessionTimeout(30);
            context.setSessionCookieName("JSESSIONID");
            context.setUseHttpOnly(false);
            });
    }

   /* @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        ((TomcatEmbeddedServletContainerFactory) container).addContextCustomizers(context -> context.setUseHttpOnly(false));

    }*/
}
