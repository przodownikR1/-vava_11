package pl.java.scalatech.web.security;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminAuth")
public class AdminAuthController {

    @RequestMapping("")
    String check() {
        return "admin check";

    }
}
