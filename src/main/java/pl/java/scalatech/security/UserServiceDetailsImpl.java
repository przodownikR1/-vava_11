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

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.SecurityComponent;
import pl.java.scalatech.repository.UserRepository;

@SecurityComponent
@DependsOn("stringEncryptor")
@Slf4j
public class UserServiceDetailsImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

<<<<<<< HEAD
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
=======
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
         UserSec sec = new UserSec(userRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("login.not.exitst")));
         log.info("+++ userSec {}",sec);
         return sec;
>>>>>>> a5cac8aa7149415e57aadb31f77fb311d36fdb92


    }

}
