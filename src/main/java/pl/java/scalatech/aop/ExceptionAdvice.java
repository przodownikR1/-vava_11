package pl.java.scalatech.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.ExceptionEntity;
import pl.java.scalatech.repository.ExceptionRepository;

@Aspect
@Component
@Slf4j
public class ExceptionAdvice {

   @Autowired
   private ExceptionRepository repo;
 
     @Pointcut("@annotation(pl.java.scalatech.annotation.CatchException) || within(@org.springframework.stereotype.Service *)  || "
             + " within(@org.springframework.stereotype.Controller *)")
    //@Pointcut("execution( * pl.java.scalatech.service.ProductService.*(..))") //regula gdzie to ma byc wyzwalane
    public void serviceExLog(){}
    
    @AfterThrowing(pointcut = "serviceExLog()", throwing = "ex")
    public Object logAfterThrowing(JoinPoint joinPoint, Throwable ex)  //kiedy zostanie wyzwolone co to ma zrobic ...
    {
        log.info("++++++++++++++++++++++++++++++++++++++++++++++++");
        String method = joinPoint.getSignature().getName();
        String clazz = joinPoint.getTarget().getClass().getSimpleName();
        repo.save(ExceptionEntity.builder().clazz(clazz).methodName(method).exMessage(ex.getMessage()).build());
        return ex;
       
    }
    
}
