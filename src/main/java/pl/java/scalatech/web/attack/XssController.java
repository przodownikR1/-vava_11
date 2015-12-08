package pl.java.scalatech.web.attack;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class XssController {
    @RequestMapping("/xss")
    String xss(Model model){
        model.addAttribute("msg", "js!!");
        return "xss";
        
    }
    
}
