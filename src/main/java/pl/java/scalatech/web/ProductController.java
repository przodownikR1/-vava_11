package pl.java.scalatech.web;

<<<<<<< HEAD
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

=======
>>>>>>> a5cac8aa7149415e57aadb31f77fb311d36fdb92
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.CurrentUser;
import pl.java.scalatech.entity.Product;
<<<<<<< HEAD
import pl.java.scalatech.entity.User;
import pl.java.scalatech.service.product.ProductService;
import pl.java.scalatech.validator.ProductValidator;
=======
import pl.java.scalatech.repository.ProductRepository;
>>>>>>> a5cac8aa7149415e57aadb31f77fb311d36fdb92
@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController extends AbstractRepoController<Product>{

    private final static String PRODUCT_VIEW = "product";
    private final static String PRODUCT_EDIT = "productEdit";
    private final static String PRODUCT_REDIRECT = "redirect:/product/";

    private final ProductRepository productRepository;

    @Autowired


    private final @NonNull ProductService productService;

    
    public ProductController(JpaRepository<Product,Long> productReposity) {
        super(productReposity);
        this.productRepository = (ProductRepository) productReposity;

     }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Product> product(@CurrentUser User user) {
        log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++   {}",user);
      // return productService.getAllByUser(user);
        return Lists.newArrayList( Product.builder().name("zarowka").price(BigDecimal.valueOf(23)).quantity(1).enable(true).build());
    }




    @Override
    protected String getView() {
        return PRODUCT_VIEW;
    }

    @Override
    protected String getEditView() {
        return PRODUCT_EDIT;
    }

    @Override
    protected Product createEmpty() {
        return new Product();
    }

    @Override
    protected String getRedirect() {
        return PRODUCT_REDIRECT;
    }

}
