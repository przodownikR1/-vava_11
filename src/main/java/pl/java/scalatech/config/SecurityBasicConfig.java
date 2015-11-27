package pl.java.scalatech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
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
@ComponentScan(basePackages = { "pl.java.scalatech.security" }, useDefaultFilters = false, includeFilters = { @Filter(SecurityComponent.class) })
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter {
    private static final int MAX_SESSIONS = 3;

    @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    static class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Autowired
        private PermissionEvaluator permissionEvaluator;

        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
            handler.setPermissionEvaluator(permissionEvaluator);
            return handler;
        }
    }

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationSuccessHandler authSuccessHandler;
        @Autowired
        private LogoutSuccessHandler logoutSuccessHander;



        @Order(2)
        @Configuration
        static class H2WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

            // @formatter:off
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                    .csrf().disable()
                    .headers().disable()
                    .requestMatchers()
                        .antMatchers("/h2/**")
                        .and()
                    .formLogin()
                        .loginPage("/login")
                        .and()
                    .authorizeRequests()
                        .anyRequest().hasRole("ADMIN");
            }
            // @formatter:on
        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**").antMatchers("/css/**").antMatchers("/js/**").antMatchers("/images/**").antMatchers("/favicon.ico");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            AccessDeniedHandlerImpl deniedhandler = new AccessDeniedHandlerImpl();
            deniedhandler.setErrorPage("/accessdenied.html");

            http
              .authorizeRequests().antMatchers("/welcome", "/api/ping", "/signup", "loginAjax","/about","/register","/currentUser","/console","/","/welcome","/login").permitAll()
              .antMatchers("/api/admin/**").hasRole("ADMIN")
              .antMatchers("/api/appContext").hasRole("ADMIN")
              .antMatchers("/api/user/**").hasRole("USER")
              .antMatchers("/currentUser").hasRole("USER")
              .antMatchers("/logout").authenticated()
              .antMatchers("/api/business**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_BUSINESS')")
              .anyRequest().authenticated();

            http.csrf().disable().headers().disable()
            .formLogin()
            .loginPage("/login").failureUrl("/login?error=true").successHandler(authSuccessHandler).defaultSuccessUrl("/")
            .permitAll()
            .and()
            .logout().logoutSuccessUrl("/welcome").invalidateHttpSession(true).logoutSuccessHandler(logoutSuccessHander).deleteCookies("JSESSIONID")
             .permitAll()
             .and()
             .exceptionHandling()
             .accessDeniedHandler(deniedhandler)
             .and()
             .sessionManagement()
             .sessionFixation().newSession()
             .maximumSessions(MAX_SESSIONS)
             .maxSessionsPreventsLogin(true);

        }
          @Autowired
          public void configureGlobal(UserDetailsService userDetailsService,AuthenticationManagerBuilder auth,PasswordEncoder passwordEncoder) throws Exception {

              auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
         /* auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
          .withUser("przodownik").password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").roles("USER").and()
          .withUser("aga").password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").roles("BUSINESS").and()
          .withUser("vava").password("$2a$10$pulPdVELrppUwOFFewJAu.hfIO5uiUsA/MOQoRKIXGNznNJQksIg.").roles("USER").and()
          .withUser("bak").password("$2a$10$yVwBhgXkVNvvNm5CI7WsJeFoS/D9pic7DhpJDE6o1IHJYnSz1re8.").roles("USER", "ADMIN");
          }*/

    }

    }



}
