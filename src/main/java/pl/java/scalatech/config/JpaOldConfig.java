package pl.java.scalatech.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan("pl.java.scalatech.repository.old")
@Profile("sqlInjection")
public class JpaOldConfig {

}
