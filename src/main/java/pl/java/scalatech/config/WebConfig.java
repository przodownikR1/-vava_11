package pl.java.scalatech.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter{

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;
    
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        registration.addInitParameter("webAllowOthers", "true");
        return registration;
    }
    
    @Override
    public  void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);

    }
}
