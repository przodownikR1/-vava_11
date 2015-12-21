package pl.java.scalatech.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

//@SecurityComponent
@Aspect
@Slf4j
public class SessionEventListeners {



    @EventListener
    @Transactional
    public void onHttpSessionDestroyed(final HttpSessionDestroyedEvent event) {
      log.info("++++ onHttpSessionDestroyed ");
    }

    @Before(value = "execution(* org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler.logout(..))")
    public void handleLogout(final JoinPoint joinPoint) {
        log.info("+++  handleLogout");
        final HttpServletRequest request = HttpServletRequest.class.cast(joinPoint.getArgs()[0]);
        final HttpSession session = request.getSession(false);
        if (session != null) {
            //session.setAttribute(HANDLE_LOGOUT_KEY, true);
        }
    }

    @EventListener
    public void onSessionFixationProtection(final SessionFixationProtectionEvent event) {
       log.info("++++ onSessionFixationProtection ");
    }

}