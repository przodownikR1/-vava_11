package pl.java.scalatech.config;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.BankAccount;
import pl.java.scalatech.repository.BankAccountRepository;
import pl.java.scalatech.repository.UserRepository;
import pl.java.scalatech.repository.old.BankAccountRepo;

@Configuration
@Slf4j
@Profile("sqlInjection")
public class SqlInjectionConfig {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private BankAccountRepo bankAccountRepo;

    @PostConstruct
    public void init() {

        for(int i =0 ;i<10;i++){
            log.info("+++ bankAccount : {}",bankAccountRepository.save(BankAccount.builder().accountNumber("12"+i).iban(""+i).saldo(new BigDecimal(i)).user(userRepository.findAll().get(0)).build()));
           log.info("{}",bankAccountRepo.getAll());
        }
    }
}
