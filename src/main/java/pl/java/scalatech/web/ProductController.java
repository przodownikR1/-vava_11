package pl.java.scalatech.web;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.service.product.ProductService;
import pl.java.scalatech.validator.ProductValidator;
@Controller
@RequestMapping("/product")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //@NonNull
public class ProductController {

    private final static String PRODUCT_VIEW = "product";
    private final static String PRODUCT_EDIT = "productEdit";

    List<Error> errors = Lists.newArrayList(new Error("sdas"),new Error("sdsvcvc"));

    @ModelAttribute
    public List<Error> error(){

        return errors;
    }


    Random r = new Random();
    @Autowired
    private ProductValidator productValidator;

    private final @NonNull ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Product> product() {
       return productService.getAll(); //productList
    }

    @RequestMapping(method = RequestMethod.GET)
    public String init(Model model) {
        model.addAttribute(new Product());
        return PRODUCT_EDIT;
    }



    @RequestMapping(value = "/{id}/test", method = RequestMethod.GET)
    @ResponseBody Product getProductByIdTest(@PathVariable("id") Product product) {
       return product;
    }


    @RequestMapping(value = "/{id}/", method = RequestMethod.GET)
    String getProductById(@PathVariable("id") Long id,Model model) {
        if (id == null) {
            model.addAttribute("product", new Product());
        } else {
            model.addAttribute("product", productService.findOne(id));
        }

        return PRODUCT_EDIT;
    }
    @SneakyThrows
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.DELETE,RequestMethod.GET,RequestMethod.POST})
    public String delete(@PathVariable("id") Long id) {
        Product product = productService.findOne(id);
        productService.delete(product);
        return PRODUCT_REDIRECT;
    }

    private final static String PRODUCT_REDIRECT = "redirect:/product/";

    @RequestMapping(value = { "", "/{id}" }, method = RequestMethod.POST)
    public String create(Product product, BindingResult result, Errors errors) {
        log.info("+++  product save :  {}",product);

        productValidator.validate(product, errors);

        if (result.hasErrors()) {
            log.info("+++  product error  {}", result);
            return PRODUCT_EDIT;
        }
        productService.save(product);
        return PRODUCT_REDIRECT;
    }

}
