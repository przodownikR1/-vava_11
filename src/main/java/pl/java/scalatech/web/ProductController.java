package pl.java.scalatech.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.annotation.CatchException;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.service.product.ProductService;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //@NonNull
public class ProductController {

    private final @NonNull ProductService productService;
    
    @RequestMapping("/products")
    public @ResponseBody List<Product> getProducts(){
        return productService.getAll();
    }
    
    @RequestMapping("/testEx")
    public @ResponseBody String testEx() throws IllegalAccessException{
         productService.testEx();
         return "error";
    }
    
    @RequestMapping("/testEx1")
    @CatchException
    public @ResponseBody String testEx1() {
         productService.testEx1();
         return "error";
    }
}
