package pl.java.scalatech.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.SecurityComponent;

@Slf4j
@SecurityComponent
public class AuthSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {

    @SuppressWarnings("unused")
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
<<<<<<< HEAD
        log.info("++++   ++++++++++++++++++++++++++++++++    authSuccessHandler           {}", userDetails);
=======
        log.info("++++                                       authSuccessHandler           {}", userDetails.getUsername());
>>>>>>> a5cac8aa7149415e57aadb31f77fb311d36fdb92

        if (userDetails.getUsername() != null) {
            log.info("!!!!!!!!!!! TODO");
        }
        return;

    }

}