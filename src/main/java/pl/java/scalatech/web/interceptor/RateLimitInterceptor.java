package pl.java.scalatech.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.service.RateLimitService;

@Slf4j
@Component
public class RateLimitInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private RateLimitService rateLimitService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long count = rateLimitService.incrementLimit(request.getRemoteAddr());
        if (request.getMethod() == "POST") { return true; }

        // don't accept more than 150 requests in less than 15 minutes
        if (count > 150) {
            response.sendError(429, "Rate limit exceeded, wait for the next quarter minute");
            return false;
        }
        response.addIntHeader("Remaining request count", (int) (150 - count));
        return true;
    }

}
