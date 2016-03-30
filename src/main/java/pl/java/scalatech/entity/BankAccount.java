package pl.java.scalatech.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(includeSuper = true)
@Builder
public class BankAccount extends PKEntity {

    private static final long serialVersionUID = 3799373003572004974L;

    private BigDecimal saldo;

    private String accountNumber;

    private String iban;
    @ManyToOne
    private User user;

}
