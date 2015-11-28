package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.Product;
import pl.java.scalatech.repository.ProductRepository;
@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController extends AbstractRepoController<Product>{

    private final static String PRODUCT_VIEW = "product";
    private final static String PRODUCT_EDIT = "productEdit";
    private final static String PRODUCT_REDIRECT = "redirect:/product/";

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(JpaRepository<Product,Long> productReposity) {
       super(productReposity);
       this.productRepository = (ProductRepository) productReposity;
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
