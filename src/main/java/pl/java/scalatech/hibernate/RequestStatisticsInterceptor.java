package pl.java.scalatech.hibernate;

import static java.lang.System.currentTimeMillis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class RequestStatisticsInterceptor implements HandlerInterceptor {

	private ThreadLocal<Long> time = new ThreadLocal<>();
	
	@Autowired
	private HibernateStatisticsInterceptor statisticsInterceptor;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    time.set(currentTimeMillis());
        statisticsInterceptor.startCounter();
        return true;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		Long queryCount = statisticsInterceptor.getQueryCount();
		if (modelAndView != null) {
			modelAndView.addObject("_queryCount", queryCount);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long duration = currentTimeMillis() - time.get();
		Long queryCount = statisticsInterceptor.getQueryCount();
		statisticsInterceptor.clearCounter();
		time.remove();
		log.info("[Time: {} ms] [Queries: {}] {} {}", duration, queryCount, request.getMethod(), request.getRequestURI());
	}


}
