package pl.java.scalatech.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import lombok.SneakyThrows;
import pl.java.scalatech.components.EmailService;


@Configuration
@Profile("mail")
@PropertySource("file:/home/przodownik/settings/mail.properties")
@ComponentScan(basePackageClasses = EmailService.class)
public class MailConfig {

    @Value("${mail.port}")//port 465
    private int port;
    @Value("${mail.host}")///smtp.gmail.com"
    private String host;
    @Value("${mail.userName}")//przodownikR1
    private String userName;
    @Value("${mail.password}")//****
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       PropertySourcesPlaceholderConfigurer pp = new PropertySourcesPlaceholderConfigurer();
       return pp;
    }



    @Bean(name = "simpleMail")
    @SneakyThrows
    JavaMailSender email() {  //MailSender
        JavaMailSenderImpl emailSender = new JavaMailSenderImpl();
        emailSender.setHost(host);
        emailSender.setPort(port);
        emailSender.setUsername(userName);
        emailSender.setPassword(password);
        Properties mailProperties = new Properties();
        mailProperties.setProperty("mail.smtp.auth", "true");
        mailProperties.setProperty("mail.smtp.socketFactory.port", "465");
        mailProperties.setProperty("mail.smtp.host", "smtp.gmail.com");
        mailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
       mailProperties.setProperty("mail.smtp.port", "465");
        emailSender.setJavaMailProperties(mailProperties);
        return emailSender;
    }

}
