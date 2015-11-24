package pl.java.scalatech.aop;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.google.common.base.Stopwatch;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceAop {

    //@Around must be  ProceedingJoinPoint proceedingJP
    
    
    @Around("execution (@pl.java.scalatech.annotation.Trace * *(..))") //pointcut
    public Object trace(ProceedingJoinPoint proceedingJP) throws Throwable { //advice
        Stopwatch sw = Stopwatch.createStarted();    
        Object obj =  proceedingJP.proceed();
        sw.stop();
        log.info("++++++  method : {} : {} ",proceedingJP.getSignature().getName(),sw.elapsed(TimeUnit.MILLISECONDS));
        return obj;
        
    }
}
