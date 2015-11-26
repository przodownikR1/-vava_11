package pl.java.scalatech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configurable
public class EncryptConfig {
    @Autowired
    private Environment environment;
   
 
    @Bean(name="stringEncryptor")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        
    }
    
 /* public static void main(String[] args) {
        PasswordEncoder pe = new BCryptPasswordEncoder();
        System.out.println(""+pe.encode("vava"));
    }*/
}
