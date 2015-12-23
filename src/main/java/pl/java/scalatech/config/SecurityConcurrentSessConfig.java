package pl.java.scalatech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import lombok.SneakyThrows;

@Configuration
@Profile("secConcurrent")
public class SecurityConcurrentSessConfig {

    public static final int MAX_SESSIONS = 1;
    
    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
    
    @Bean
    public static SessionRegistry getSessionRegistry() {
        return new SessionRegistryImpl();
    }

    @SneakyThrows
    public static HttpSecurity createHttpConcurrentStrategy(HttpSecurity http) {
        http.sessionManagement().invalidSessionUrl("/invalidSession").maximumSessions(MAX_SESSIONS).expiredUrl("/sessionError").maxSessionsPreventsLogin(true)
        .sessionRegistry(getSessionRegistry()).and().sessionFixation().migrateSession();
        return http;
    }


}
