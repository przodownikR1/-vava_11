package pl.java.scalatech.moneyEntity;

import java.math.BigDecimal;
import java.util.Locale;

import javax.money.Monetary;

import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.TestSelectorConfig;
import pl.java.scalatech.entity.Room;
import pl.java.scalatech.repository.RoomRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestSelectorConfig.class)
@Slf4j
@ActiveProfiles("test")
public class MoneyEntityTest {

    @Autowired
    private RoomRepository roomRepository;
    @Test
    public void shouldSaveAndThenRead(){
        
        Room room = new Room("oneBed",Money.of(new BigDecimal(130), Monetary.getCurrency(Locale.US)));
        roomRepository.save(room);
        log.info(" room  {}   ", roomRepository.findAll());  
        
    }
}
