package pl.java.scalatech.config;

import java.util.Arrays;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;


//@Configuration
public class TomcatConfig {
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
      TomcatEmbeddedServletContainerFactory tomcat = new      TomcatEmbeddedServletContainerFactory() ;
      tomcat.setTomcatContextCustomizers(Arrays.asList(new CustomCustomizer()));
      tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
      tomcat.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/400"));
      tomcat.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
      return tomcat;
    }

    private Connector initiateHttpConnector() {
      Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
      connector.setScheme("http");
      connector.setPort(9113);
      connector.setSecure(false);
      
      return connector;
    }
    static class CustomCustomizer implements TomcatContextCustomizer {
        @Override
        public void customize(Context context) {
            context.setUseHttpOnly(false);
            context.setSessionTimeout(15);
        }

    }
}
