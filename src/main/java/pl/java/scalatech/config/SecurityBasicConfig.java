package pl.java.scalatech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.SecurityComponent;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(EncryptConfig.class)
@Slf4j
@ComponentScan(basePackages = { "pl.java.scalatech.security" }, useDefaultFilters = false, includeFilters = { @Filter(SecurityComponent.class) })
public class SecurityBasicConfig {
 

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
    public static class ConsoleWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http.antMatcher("/console/**").authorizeRequests().anyRequest().authenticated()//authorizeRequests().anyRequest().hasRole("ADMIN")
            .and().httpBasic();//.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.csrf().disable().headers().disable();     
            // @formatter:on         
        }
        

        @Autowired
        public void configureGlobal(UserDetailsService userDetailsService, AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
                throws Exception {
            log.info("password Encoding {}", passwordEncoder);
            auth.userDetailsService(userDetailsService);
           
        }
        
    }

    @Configuration
    @Order(2)
    public static class FormWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private static final int MAX_SESSIONS = 3;
        @Autowired
        private AuthenticationSuccessHandler authSuccessHandler;

        private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler;

        @Autowired
        private LogoutSuccessHandler logoutSuccessHander;
        
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**").antMatchers("/css/**").antMatchers("/js/**").antMatchers("/images/**").antMatchers("/favicon.ico");
        }

        @Bean
        public SessionRegistry getSessionRegistry() {
            return new SessionRegistryImpl();
        }
        
        @Bean
        public static HttpSessionEventPublisher httpSessionEventPublisher() {
            return new HttpSessionEventPublisher();
        } 

        @Autowired
        private SessionAuthenticationStrategy sessionStrategy;

        @Bean
        public SessionAuthenticationStrategy getSessionAuthStrategy(SessionRegistry sessionRegistry) {
            ConcurrentSessionControlAuthenticationStrategy controlAuthenticationStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
            controlAuthenticationStrategy.setMaximumSessions(2);
            controlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);

            return controlAuthenticationStrategy;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            AccessDeniedHandlerImpl deniedhandler = new AccessDeniedHandlerImpl();
            deniedhandler.setErrorPage("/accessdenied.html");
         // @formatter:off
            http.authorizeRequests()
            .antMatchers("/welcome", "/api/ping", "/api/cookie", "/signup", "loginAjax", "/about", "/register", "/currentUser", "/","/welcome", "/login").permitAll()
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .antMatchers("/api/appContext").hasRole("ADMIN")
            .antMatchers("/role/**").hasRole("ADMIN")
            .antMatchers("/api/user/**").hasRole("USER")
            .antMatchers("/currentUser").hasRole("USER")
            .antMatchers("/logout").authenticated()
            .antMatchers("/api/business**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_BUSINESS')").anyRequest().authenticated();

            http.csrf().disable().formLogin().loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/").permitAll()
            .and()
            .logout().logoutSuccessUrl("/welcome").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll()
            .and()
            .exceptionHandling().accessDeniedHandler(deniedhandler)
            .and()
            .sessionManagement().sessionAuthenticationStrategy(sessionStrategy).sessionFixation().migrateSession();
            
            
            http.sessionManagement().invalidSessionUrl("/login?invalid=true").maximumSessions(MAX_SESSIONS).
            maxSessionsPreventsLogin(true).expiredUrl("/login?expired=true");
            
            
            // @formatter:on
        }

        @Autowired
        public void configureGlobal(UserDetailsService userDetailsService, AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder)
                throws Exception {
            log.info("password Encoding {}", passwordEncoder);
            auth.userDetailsService(userDetailsService);
            
        }

    }
  

    
}