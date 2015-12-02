package pl.java.scalatech.web.old;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.BankAccount;
import pl.java.scalatech.repository.old.BankAccountRepo;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SqlInjectController {

    private final @NonNull BankAccountRepo repo;
    
    @RequestMapping("/sqlInject/{param}")
    public List<BankAccount> getByParam(@PathVariable String param){
        return repo.getAllByParam(param);
        
    }
     
}
