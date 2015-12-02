package pl.java.scalatech.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccount extends PKEntity{
    
    private BigDecimal saldo;
    
    private String accountNumber;
    
    private String iban;
    @ManyToOne
    private User user;

}
