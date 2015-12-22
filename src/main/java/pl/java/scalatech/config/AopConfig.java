package pl.java.scalatech.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;


@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages="pl.java.scalatech.aop")
@Profile("aop")
public class AopConfig {



}
