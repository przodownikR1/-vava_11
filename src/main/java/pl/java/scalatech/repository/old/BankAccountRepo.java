package pl.java.scalatech.repository.old;

import java.util.List;

import pl.java.scalatech.entity.BankAccount;

public interface BankAccountRepo {

    BankAccount getById(Long id);
    
    BankAccount save(BankAccount ba);
    
    void delete(Long id);
    
    List<BankAccount> getAll();
    
    List<BankAccount> getAllByParam(String param);
    
}
