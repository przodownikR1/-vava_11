package pl.java.scalatech.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import groovy.util.logging.Slf4j;
import pl.java.scalatech.entity.Product;

@Component
@lombok.extern.slf4j.Slf4j
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (Product.class).isAssignableFrom(clazz); //klasa ktora chcemy validowac
    }

    @Override
    public void validate(Object target, Errors errors) {
          Product product = (Product)target;
        
        ValidationUtils.rejectIfEmpty(errors, "name","name.required"); //rejestruj blad 
        
        if(product.isEnable() && !"opona".equalsIgnoreCase(product.getName())){
            
            errors.rejectValue("common", "common.shouldStartWithCapitalLetter");
            
            log.info("+++++  product validator {}",errors);
        }
        
    }

}
