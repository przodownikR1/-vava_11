package pl.java.scalatech.components;



import static java.time.LocalTime.now;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Scheduler {
    
    @Autowired
    Work wok;
    
   // @Scheduled(fixedRate=2000)
    public void echo(){
        log.info("+++ {} - > {}", now(),Thread.currentThread().getName());
        wok.print();
    }
    
}
