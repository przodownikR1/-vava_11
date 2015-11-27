package pl.java.scalatech.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.repository.UserRepository;

@Controller
@RequestMapping("/user")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) //@NonNull
public class UserController {


    private final @NonNull UserRepository userRepository;

    @RequestMapping("/")
    public String user(Model model){
        model.addAttribute("user", new User());
        return "user";
    }

    @RequestMapping(value = "/{id}/test", method = RequestMethod.GET)
    @ResponseBody User getUserByIdTest(@PathVariable("id") User user) {
       return user;
    }
}
