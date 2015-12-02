package pl.java.scalatech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
    @Autowired
    private AuthenticationSuccessHandler authSuccessHandler;

    private SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHander;


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


        @Order(3)
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


        @Order(2)
        @Configuration
        static class ApiWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

            // @formatter:off
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .httpBasic().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            }
            // @formatter:on
        }
*/

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
              //.antMatchers("/role").hasRole("ADMIN")
             // .antMatchers("/role/**").hasRole("ADMIN")
              .antMatchers("/api/user/**").hasRole("USER")
              .antMatchers("/currentUser").hasRole("USER")
              .antMatchers("/logout").authenticated()
              .antMatchers("/api/business**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_BUSINESS')")
              .anyRequest().authenticated();

            http.csrf().disable()
            .formLogin()
            .loginPage("/login").failureUrl("/login?error=true").defaultSuccessUrl("/")
            .permitAll()
            .and()
            .logout().logoutSuccessUrl("/welcome").invalidateHttpSession(true).deleteCookies("JSESSIONID")
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
             log.info("password Encoding {}",passwordEncoder);
              auth.userDetailsService(userDetailsService);
        /*  auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
          .withUser("przodownik").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("USER").and()
          .withUser("aga").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("BUSINESS").and()
          .withUser("vava").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("USER").and()
          .withUser("bak").password("$2a$10$vGdVdtvx9jGTVs1uuywXyOiYovelvWWUFBIMbS5pSNuWmcCZlx.86").roles("USER", "ADMIN");
          }*/
          }

    
    }