package pl.java.scalatech.repository.old.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import lombok.extern.slf4j.Slf4j;
import pl.java.scalatech.entity.BankAccount;
import pl.java.scalatech.repository.old.BankAccountRepo;
@Repository
@Transactional(readOnly=true)
@Slf4j
public class BankAccountRepoImpl  implements BankAccountRepo{

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    
    @Override
    public BankAccount getById(Long id) {
        BankAccount result = null;
        try{
         result = entityManagerFactory.createEntityManager().createQuery("FROM BankAccount where id = :id",BankAccount.class).setParameter("id", id).getSingleResult();
        }catch(NoResultException nre){
            result = null;
        }
        return result;
    }

    @Override
    @Transactional
    public BankAccount save(BankAccount ba) {
        log.info("======================================");
        if(ba.getId() == null || getById(ba.getId())== null){
            entityManagerFactory.createEntityManager().persist(ba);   
        }
        else if(getById(ba.getId())!= null ){
            ba =  entityManagerFactory.createEntityManager().merge(ba);
        }else{
            
        }
         
         return ba;
    }

    @Override
    @Transactional
    public void delete(Long id) {
         BankAccount loaded = Preconditions.checkNotNull(getById(id));
         entityManagerFactory.createEntityManager().remove(loaded);
        
    }

    @Override
    public List<BankAccount> getAll() {
        return entityManagerFactory.createEntityManager().createQuery("FROM BankAccount").getResultList();
    }

  /*  @Override
    public List<BankAccount> getAllByParam(String param) {
        String query = "SELECT * FROM BANK_ACCOUNT WHERE  IBAN= "+ param;
        List<BankAccount> accounts = entityManagerFactory.createEntityManager().createNativeQuery(query).getResultList();
        log.info("+++  accounts {}",accounts);
        return accounts;
    }*/
    @Override
    public List<BankAccount> getAllByParam(String param) {
        String query = "FROM BankAccount WHERE  IBAN= "+ param;
        List<BankAccount> accounts = entityManagerFactory.createEntityManager().createQuery(query).getResultList();
        log.info("+++  accounts {}",accounts);
        return accounts;
    }

    
}
