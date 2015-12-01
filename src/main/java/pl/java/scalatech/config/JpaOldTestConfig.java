package pl.java.scalatech.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@ComponentScan("pl.java.scalatech.repository.old")
@EntityScan(basePackages="pl.java.scalatech.entity")
@Import({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Profile("sqlInjection")
public class JpaOldTestConfig {

}
