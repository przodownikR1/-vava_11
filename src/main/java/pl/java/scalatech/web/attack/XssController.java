package pl.java.scalatech.web.attack;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class XssController {
    @RequestMapping("/xss")
    String xss(Model model){
        model.addAttribute("msg", "Hi : <script>alert(document.cookie)</script>");
        log.info("model   :  {}",model);
        return "xss";
        
    }
    
}
