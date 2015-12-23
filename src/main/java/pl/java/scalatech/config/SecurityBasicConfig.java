package pl.java.scalatech.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.SecurityComponent;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({EncryptConfig.class,SecurityLoggerConfig.class,SecurityConcurrentSessConfig.class})
@Slf4j
@ComponentScan(basePackages = { "pl.java.scalatech.security" }, useDefaultFilters = false, includeFilters = { @Filter(SecurityComponent.class) })
public class SecurityBasicConfig extends WebSecurityConfigurerAdapter{


    /*  @Configuration
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    @Order(4)
    static class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
        @Autowired
        private PermissionEvaluator permissionEvaluator;

        @Autowired
        SecurityEvaluationContextExtension securityEvaluationContextExtension;

        @Override
        protected MethodSecurityExpressionHandler createExpressionHandler() {
            DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler();
            handler.setPermissionEvaluator(permissionEvaluator);
            return handler;
        }
    }
*/

   /* @Order(1)
    @Configuration
    static class H2WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/console/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            .and()
            .httpBasic();.and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
        @Autowired
        public void configureGlobal(UserDetailsService userDetailsService, AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
            auth.userDetailsService(userDetailsService);

        }

    }*/

        @Autowired
        private AuthenticationSuccessHandler authSuccessHandlerImpl;

        @Autowired
        private AuthenticationFailureHandler authFailureHandlerImpl;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/assets/**").antMatchers("/css/**").antMatchers("/js/**").antMatchers("/images/**").antMatchers("/favicon.ico");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            AccessDeniedHandlerImpl deniedhandler = new AccessDeniedHandlerImpl();
            deniedhandler.setErrorPage("/accessdenied.html");
            http.authorizeRequests()
                    .antMatchers("/welcome", "/api/ping", "/api/cookie", "/signup", "loginAjax", "/about", "/register", "/currentUser",  "/", "/welcome")
                    .permitAll().antMatchers("/api/admin/**").hasRole("ADMIN")
                    .antMatchers("/api/appContext").hasRole("ADMIN")
                    .antMatchers("/role/**").hasRole("ADMIN")
                    .antMatchers("/products/**").hasRole("USER")
                    .antMatchers("/api/user/**").hasRole("USER")
                    .antMatchers("/currentUser").hasRole("USER")
                    .antMatchers("/api/business**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_BUSINESS')").anyRequest().authenticated();

                     http.csrf().disable()
                     .formLogin().loginPage("/login").successHandler(authSuccessHandlerImpl).failureHandler(authFailureHandlerImpl).failureUrl("/login?error=true").defaultSuccessUrl("/").permitAll()
                     .and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/secContext").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll()
                    .and().exceptionHandling().accessDeniedHandler(deniedhandler);
                     // @formatter:on
        }
        @Autowired
        public void configureGlobal(UserDetailsService userDetailsService, AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
            auth.userDetailsService(userDetailsService);

        }

}
/*
 * auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
 * .withUser("przodownik").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("USER").and()
 * .withUser("aga").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("BUSINESS").and()
 * .withUser("vava").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("USER").and()
 * .withUser("bak").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("USER", "ADMIN");
 * }
 */
/*
 * @Autowired
 * private SessionAuthenticationStrategy sessionStrategy;
 * @Bean
 * public SessionAuthenticationStrategy getSessionAuthStrategy(SessionRegistry sessionRegistry) {
 * ConcurrentSessionControlAuthenticationStrategy controlAuthenticationStrategy =
 * new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry);
 * controlAuthenticationStrategy.setMaximumSessions(MAX_SESSIONS);
 * controlAuthenticationStrategy.setExceptionIfMaximumExceeded(true);
 * return controlAuthenticationStrategy;
 * }
 */