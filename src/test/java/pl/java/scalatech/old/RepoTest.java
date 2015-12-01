package pl.java.scalatech.old;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.config.JpaOldTestConfig;
import pl.java.scalatech.entity.BankAccount;
import pl.java.scalatech.repository.old.BankAccountRepo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=JpaOldTestConfig.class)
@ActiveProfiles("sqlInjection")
@Slf4j
@Transactional
public class RepoTest {
    
    @Autowired
    private BankAccountRepo bankAccountRepo;
    
    @Test
    public void shouldRepoWork(){
        bankAccountRepo.save(BankAccount.builder().accountNumber("23232").iban("333333333333324234214234").saldo(BigDecimal.valueOf(2311)).build());
        
        log.info("+++++++++  {}", bankAccountRepo.getAll());
    }

}
