package pl.java.scalatech.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.SecurityComponent;

@SecurityComponent
@Slf4j
public class LoginServiceImpl implements LoginService {


    @Autowired(required = false)
    AuthenticationManager authenticationManager;

    @Override
    public LoginStatus getStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
            return new LoginStatus(true, auth.getName());
        }
        return new LoginStatus(false, null);
    }

    @Override
    public LoginStatus login(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        try {
            Authentication auth = authenticationManager.authenticate(token);
            log.info("++++         Login succeeded!");
            SecurityContextHolder.getContext().setAuthentication(auth);
            return new LoginStatus(auth.isAuthenticated(), auth.getName());
        } catch (BadCredentialsException e) {
            return new LoginStatus(false, null);
        }
    }
}