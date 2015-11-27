package pl.java.scalatech.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.security.LoginService;
import pl.java.scalatech.security.LoginStatus;

@RestController
@RequestMapping("/api/login")
@Slf4j
public class LoginController {

    @Autowired
    LoginService loginService;

    public LoginStatus getStatus() {
        return loginService.getStatus();
    }

    @RequestMapping(method = RequestMethod.POST)
    public LoginStatus login(@RequestParam("username") String username,
                             @RequestParam("password") String password) {
        log.info("ajax controller");
        return loginService.login(username, password);
    }
}