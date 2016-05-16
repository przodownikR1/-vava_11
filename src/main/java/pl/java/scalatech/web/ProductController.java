package pl.java.scalatech.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.CurrentUser;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.repository.ProductRepository;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.security.UserSec;
import pl.java.scalatech.service.product.ProductService;

@Controller
@RequestMapping("/products")
@Slf4j
public class ProductController extends AbstractRepoController<Product>{

    private final static String PRODUCT_VIEW = "products";
    private final static String PRODUCT_EDIT = "productEdit";
    private final static String PRODUCT_REDIRECT = "redirect:/products/";

     private final ProductRepository productRepository;
     private final UserRepository userRepository;  
     private final ProductService productService;
     
     
    @Autowired
    public ProductController(JpaRepository<Product,Long> productReposity, UserRepository userRepository, ProductService productService) {
        super(productReposity);
        this.userRepository = userRepository;
        this.productRepository = (ProductRepository) productReposity;
        this.productService = productService;

     }
    
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    String getAll(@CurrentUser UserSec user,Model model) {
        User loaded = userRepository.findByLogin(user.getUsername()).orElseThrow(()->new IllegalArgumentException("user not exists or not logged" ));
        model.addAttribute(productRepository.findByOwner(loaded));
        return PRODUCT_VIEW;
    }

    @Override
    @RequestMapping(value = { "", "/{id}" }, method = RequestMethod.POST)
    public String create(@Valid Product product, BindingResult result, Errors errors) {
        log.info("+++  {} save :  {}", product);
        if (result.hasErrors()) {            
            return getEditView();
        }
        //productRepository.save(product);
        productService.saveEm(product);
        
        return getRedirect();
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
