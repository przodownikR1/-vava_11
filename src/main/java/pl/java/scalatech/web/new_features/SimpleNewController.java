package pl.java.scalatech.web.new_features;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simpleNew")
public class SimpleNewController {


    @GetMapping
    String getAction(){
        return "ok";

    }

}
