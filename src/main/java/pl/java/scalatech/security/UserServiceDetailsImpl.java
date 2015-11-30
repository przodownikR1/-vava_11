package pl.java.scalatech.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.SecurityComponent;
import pl.java.scalatech.entity.User;
import pl.java.scalatech.repository.UserRepository;


@Slf4j
@SecurityComponent
@DependsOn("stringEncryptor")
public class UserServiceDetailsImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User user = findUserByLoginOrMail(username);
        if (user == null) { throw new UsernameNotFoundException("login.not.exists"); }
        log.info("++++++++++++++++++++++    {}",user);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword());

        try {
            Authentication auth = authenticationManager.authenticate(token);
            log.info("++++         Login succeeded!");
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        catch(Throwable th){
            log.info("+++++++++++++ ");
        }
        return user;
    }

    private User findUserByLoginOrMail(String loginOrMail) {
        User user = null;
        try {
            // TODO dao problem
            user = userRepository.findByLogin(loginOrMail);
        } catch (Exception e) {
            log.error("Login or mail not exists : {}  , exception {}", loginOrMail, e);
        }

        return user;
    }

}
