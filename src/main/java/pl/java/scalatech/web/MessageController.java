package pl.java.scalatech.web;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.SneakyThrows;
import pl.java.scalatech.entity.User;

@Controller                 //mvc:view-controller
@RequestMapping("/message")
public class MessageController {

    private final String PRODUCT = "product";
    
    @Resource
    private org.springframework.core.io.Resource picture;

    
    
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
    
    @RequestMapping("/userConvertTest/{userId}")
    @ResponseBody User test(@PathVariable("userId")User user){
        return user;
    }
    
    @SneakyThrows
    @RequestMapping(value = "/picture", method = RequestMethod.GET,headers = "Accept=image/jpeg, image/jpg, image/png, image/gif")
    public @ResponseBody
    byte[] getPhoto() throws IllegalAccessException {
    
        byte[] userPhotoBytes =null;// user.getPhoto();

        if (userPhotoBytes == null) {
            userPhotoBytes = new byte[(int) picture.contentLength()];
            picture.getInputStream().read(userPhotoBytes);
        }

        return userPhotoBytes;
    }
    
    
}
