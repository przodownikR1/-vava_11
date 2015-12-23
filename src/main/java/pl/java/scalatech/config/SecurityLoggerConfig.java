package pl.java.scalatech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.event.LoggerListener;

@Configuration
@Profile("secLogger")
public class SecurityLoggerConfig {

    @Bean
    public LoggerListener loggerListener() {
        LoggerListener loggerListener = new LoggerListener();
        return loggerListener;
    }

    @Bean
    public org.springframework.security.access.event.LoggerListener eventLoggerListener() {
        org.springframework.security.access.event.LoggerListener eventLoggerListener = new org.springframework.security.access.event.LoggerListener();
        return eventLoggerListener;
    }

}
