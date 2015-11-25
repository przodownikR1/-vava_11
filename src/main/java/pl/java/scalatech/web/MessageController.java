package pl.java.scalatech.web;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MessageController {

    @Autowired
    MessageSource messageSource;
    
    @RequestMapping("/message/{name}")
     String hello(@PathVariable("name")String name,Model model ,Locale locale){ ///Model = Map
        log.info("++++  {}",name);
        model.addAttribute("message", messageSource.getMessage("hello", new Object[]{name} , locale));
        return "product";
    }
    
}
