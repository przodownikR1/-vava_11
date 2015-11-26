package pl.java.scalatech.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.service.product.ProductService;
@Controller
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //@NonNull
public class ProductController {

    private final static String PRODUCT_VIEW = "product";
    private final static String PRODUCT_EDIT = "productEdit";
    private final static String PRODUCT_REDIRECT = "redirect:/product/";
    
    
    private final @NonNull ProductService productService;
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getAll(Model model) {
        model.addAttribute("products",productService.getAll());
        return PRODUCT_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String init(Model model) {
        model.addAttribute(new Product());
        return PRODUCT_EDIT;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    String getProductById(@PathVariable("id") Long id, Model model) {
        if (id == null) {
            model.addAttribute("product", new Product());
        } else {
            model.addAttribute("product", productService.findOne(id));
        }

        return PRODUCT_EDIT;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        Product product = productService.findOne(id);
        productService.delete(product);
        return PRODUCT_REDIRECT;
    }

    @RequestMapping(value = { "", "/{id}" }, method = RequestMethod.POST)
    public String create(@Valid Product product, BindingResult result, Errors errors) {
        log.info("+++  product save :  {}",product);
        
        if (result.hasErrors()) {
            log.info("+++  product error  {}", result);
            return PRODUCT_EDIT;
        }
        productService.save(product);
        return PRODUCT_REDIRECT;
    }
    
}
