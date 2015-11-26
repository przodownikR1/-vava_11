package pl.java.scalatech.web;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Controller                 //mvc:view-controller
@RequestMapping("/message")
@Slf4j
public class MessageController {

    private final String PRODUCT = "product";
    
    @Autowired
    MessageSource messageSource;
    
    @RequestMapping("/{name}/{lastName}")
    String hello(@PathVariable("name") String name, @PathVariable("lastName")String lastName,  Model model ,Locale locale){ ///Model = Map
        
        model.addAttribute("message", messageSource.getMessage("hello", new Object[]{name,lastName} , locale));
        return PRODUCT;
    }
    
    
    @RequestMapping("/{name}/{lastName}/{age}")
    String hello2(@PathVariable("name") String name, @PathVariable("lastName")String lastName,  Model model ,Locale locale){ ///Model = Map
        
        model.addAttribute("message", messageSource.getMessage("hello", new Object[]{name,lastName} , locale));
        return PRODUCT;
    }
    
    
    @RequestMapping("/redirect")
    String redirect( Model model ,Locale locale){ ///Model = Map
        
       
        return "redirect:/message/slawek/borowiec";
    }
    
    
}
