package pl.java.scalatech.config;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.converters.AmountFormatAnnotationFormatterFactory;
import pl.java.scalatech.converters.AmountFormatter;
import pl.java.scalatech.converters.LongToUserConverter;
import pl.java.scalatech.converters.StringToUserConverter;
import pl.java.scalatech.web.interceptor.PerformanceInterceptor;


@Configuration
@Slf4j
@ComponentScan(basePackages={"pl.java.scalatech.converters"})
public class WebConfig  extends WebMvcConfigurerAdapter{

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;
    
    @Autowired
    private StringToUserConverter stringToUserConverter;
    @Autowired
    private LongToUserConverter longToUserConverter;
    @Autowired
    private AmountFormatter amountFormatter;
    @Autowired
    private AmountFormatAnnotationFormatterFactory amountFormatAnnotationFormatterFactory;
    
    
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverter(stringToUserConverter);
        registry.addConverter(longToUserConverter);
        registry.addFormatter(amountFormatter);
        registry.addFormatterForFieldAnnotation(amountFormatAnnotationFormatterFactory);
    }
    
    @Bean
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        registration.addInitParameter("webAllowOthers", "true");
        return registration;
    }
    
  
    
    @Bean
    public PerformanceInterceptor perfInterceptor() {
        return new PerformanceInterceptor();
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
          registry.addViewController("/h3").setViewName("product");
          registry.addViewController("/login").setViewName("login");
          registry.addViewController("/logout").setViewName("logout");
          registry.addViewController("/").setViewName("welcome");
          registry.addViewController("/accessdenied").setViewName("accessdenied");
    }

        
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  //mapujemy statyczne zasoby
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(3000);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/").setCachePeriod(0);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/resources/images/").setCachePeriod(3000);
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico").setCachePeriod(3000);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/").setCachePeriod(3000);

    }
    
    @Override
    public  void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
        //added !!

    }
    
    
    
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        exceptionResolvers.add(new MyExceptionHandlerResolver());
    }
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }
    
    class MyExceptionHandlerResolver implements HandlerExceptionResolver {
        @Override
        public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            try {
                log.error("An error has occured: {}", ex.getMessage());

                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return new ModelAndView("myError");
            } catch (Exception handlerException) {
                log.warn("Handling of [{}] resulted in Exception", ex.getClass().getName(), handlerException);
            }
            return null;
        }
    }
}
