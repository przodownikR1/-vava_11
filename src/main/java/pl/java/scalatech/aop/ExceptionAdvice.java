package pl.java.scalatech.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javassist.bytecode.stackmap.BasicBlock.Catch;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.annotation.CatchException;
import pl.java.scalatech.entity.ExceptionEntity;
import pl.java.scalatech.repository.ExceptionRepository;

@Aspect
@Component
@Slf4j
public class ExceptionAdvice {

    @Autowired
    private ExceptionRepository repo;

    @Pointcut("@annotation(ce)")   
    public void serviceExLogAnnotation(CatchException ce) {
    }
    
    @AfterThrowing(value = "serviceExLogAnnotation(c)",throwing = "ex")
    public void logAfterThrowingAnnotation(JoinPoint joinPoint,CatchException c,Throwable ex)  
    {
        String method = joinPoint.getSignature().getName();
        String clazz = joinPoint.getTarget().getClass().getSimpleName();
        if(c.sendEmail()){
        log.info("method: {} , class : {} ,  send email to :  {} -> {}",method,clazz, c.mailAddress() ,ex.getMessage());
           }
        
    }
    
    @Pointcut("within(@org.springframework.stereotype.Controller *)  ")   
    public void serviceExLog() {
    }
   
    @AfterThrowing(value = " serviceExLog()  ", throwing = "ex")
    public Object logAfterThrowing(JoinPoint joinPoint, Throwable ex)  
    {
        log.info("++++++++++++++++++++++++++++++++++++++++++++++++ {}",joinPoint.getTarget());
        String method = joinPoint.getSignature().getName();
        String clazz = joinPoint.getTarget().getClass().getSimpleName();
        repo.save(ExceptionEntity.builder().clazz(clazz).methodName(method).exMessage(ex.getMessage()).build());
        return ex;
    }
    
   

}
