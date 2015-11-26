package pl.java.scalatech.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.SecurityComponent;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(EncryptConfig.class)
@Slf4j
@ComponentScan(basePackages = { "pl.java.scalatech.security" }, useDefaultFilters = false, includeFilters = {  @Filter(SecurityComponent.class) })
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {
    private static final int  MAX_SESSIONS    = 3;
    
    
    @Configuration
    @Order(1)                                                        
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationSuccessHandler authSuccessHandler;
        @Autowired
        private LogoutSuccessHandler logoutSuccessHander;
      
        
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**").antMatchers("/css/**").antMatchers("/js/**").antMatchers("/images/**").antMatchers("/favicon.ico");
        }
        
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            AccessDeniedHandlerImpl deniedhandler = new AccessDeniedHandlerImpl();
            deniedhandler.setErrorPage("/accessdenied.html");
            http
              .authorizeRequests().antMatchers("/welcome", "/api/ping", "/signup", "/about","/register","/currentUser","/console").permitAll()
              .antMatchers("/api/admin/**").hasRole("ADMIN")
              .antMatchers("/api/appContext").hasRole("ADMIN")
              .antMatchers("/api/user/**").hasRole("USER")
              .antMatchers("/currentUser").hasRole("USER")
              .antMatchers("/api/business**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_BUSINESS')")
              .anyRequest().authenticated();
            
            http.csrf().disable()
            .formLogin()
            .loginPage("/login").failureUrl("/login?error=true").successHandler(authSuccessHandler).defaultSuccessUrl("/")
            .permitAll()
            .and()
            .logout().logoutSuccessUrl("/welcome").invalidateHttpSession(true).logoutSuccessHandler(logoutSuccessHander).deleteCookies("JSESSIONID")
             .permitAll()
             .and()
             /*.exceptionHandling()
             .accessDeniedHandler(deniedhandler)
             .and()*/
             .sessionManagement()
             .sessionFixation().newSession()
             .maximumSessions(MAX_SESSIONS)
             .maxSessionsPreventsLogin(true);
           
        }
          @Autowired
          public void configureGlobal(AuthenticationManagerBuilder auth,PasswordEncoder passwordEncoder) throws Exception {
            
       
          auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
          .withUser("przodownik").password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").roles("USER").and()
          .withUser("aga").password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").roles("BUSINESS").and()
          .withUser("vava").password("$2a$10$pulPdVELrppUwOFFewJAu.hfIO5uiUsA/MOQoRKIXGNznNJQksIg.").roles("USER").and()
          .withUser("bak").password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").roles("USER", "ADMIN");
          }
         
    }
        

 

    
}
