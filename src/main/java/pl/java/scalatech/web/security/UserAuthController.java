package pl.java.scalatech.web.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/userAuth")
public class UserAuthController {

    @RequestMapping("")
    String check() {
        return "user check";

    }
}
