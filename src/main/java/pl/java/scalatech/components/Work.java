package pl.java.scalatech.components;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Work {
    
    
    @Async
    public void print(){
        log.info("+++  {}",Thread.currentThread().getName() );  
    }
    
}
