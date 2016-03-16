package pl.java.scalatech.config;

import java.util.List;

import javax.servlet.Filter;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.converters.AmountFormatAnnotationFormatterFactory;
import pl.java.scalatech.converters.AmountFormatter;
import pl.java.scalatech.converters.LongToUserConverter;
import pl.java.scalatech.converters.StringToUserConverter;
import pl.java.scalatech.hibernate.RequestStatisticsInterceptor;
import pl.java.scalatech.web.interceptor.PerformanceInterceptor;
import pl.java.scalatech.web.interceptor.RateLimitInterceptor;

@Configuration
@Slf4j
@ComponentScan(basePackages = { "pl.java.scalatech.converters", "pl.java.scalatech.web.interceptor" , "pl.java.scalatech.hibernate"})
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LocaleChangeInterceptor localeChangeInterceptor;

    @Autowired
    private RateLimitInterceptor rateLimitInterceptor;

    @Autowired
    private StringToUserConverter stringToUserConverter;
    @Autowired
    private LongToUserConverter longToUserConverter;
    @Autowired
    private AmountFormatter amountFormatter;
    @Autowired
    private AmountFormatAnnotationFormatterFactory amountFormatAnnotationFormatterFactory;


    @Bean
    public Filter etagFilter() {
    return new ShallowEtagHeaderFilter();
    }

    /*@Bean
    public FilterRegistrationBean shallowEtagHeaderFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ShallowEtagHeaderFilter());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        registration.addUrlPatterns("/*");
        return registration;
    }*/
    @Autowired
    private PerformanceInterceptor perf;

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private RequestStatisticsInterceptor requestStatisticsInterceptor;

    @Override
    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping hm = super.requestMappingHandlerMapping();
        hm.setRemoveSemicolonContent(false);
        return hm;
    }


    @Override   // spring boot juz to ma
    public Validator getValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }

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
    Resource picture() {
        return new org.springframework.core.io.ClassPathResource("new_mg.png");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/h3").setViewName("product");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/loginAjax").setViewName("loginAjax");
        registry.addViewController("/logThyme").setViewName("logThyme");
        registry.addViewController("/logout").setViewName("logout");
        registry.addViewController("/").setViewName("welcome");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/clickjack").setViewName("clickjack");
        registry.addViewController("/csrf").setViewName("csrf");
        registry.addViewController("/accessdenied").setViewName("accessdenied");
        registry.addViewController("/sessionError").setViewName("sessionError");
        registry.addViewController("/invalidSession").setViewName("invalidSession");
        registry.addViewController("/400").setViewName("/error/400");
        registry.addViewController("/404").setViewName("/error/404");

    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {  // mapujemy statyczne zasoby
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(3000);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/").setCachePeriod(0);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/resources/images/").setCachePeriod(3000);
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/favicon.ico").setCachePeriod(3000);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/").setCachePeriod(3000);

    }

   
    
    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor);
        registry.addInterceptor(perf);
        registry.addInterceptor(requestStatisticsInterceptor);
        registry.addInterceptor(rateLimitInterceptor).addPathPatterns("/**");

    }

    @Bean
    // @Profile("converter")
    public DomainClassConverter<?> domainClassConverter() {
        return new DomainClassConverter<>(mvcConversionService());
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        // xceptionResolvers.add(new MyExceptionHandlerResolver());

    }

    @Bean
    public FilterRegistrationBean filterRegistrationBeanEncoding() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }


    @Bean
    public FilterRegistrationBean filterRegistrationBeanHidden() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new HiddenHttpMethodFilter());
        return registrationBean;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver standardServletMultipartResolver = new StandardServletMultipartResolver();
        return standardServletMultipartResolver;
    }


    @Bean
    public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver(){
        return new AuthenticationPrincipalArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticationPrincipalArgumentResolver());
    }


    /*
     * class MyExceptionHandlerResolver implements HandlerExceptionResolver {
     * @Override
     * public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
     * try {
     * log.error("An error has occured: {}", ex.getMessage());
     * request.setAttribute("message", ex.getMessage());
     * return new ModelAndView("errors");
     * } catch (Exception handlerException) {
     * log.warn("Handling of [{}] resulted in Exception", ex.getClass().getName(), handlerException);
     * }
     * return new ModelAndView("errors");
     * }
     * }
     */
}
