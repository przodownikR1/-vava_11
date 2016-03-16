package pl.java.scalatech.entity;

import javax.persistence.Convert;
import javax.persistence.Entity;

import org.javamoney.moneta.Money;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Room extends AbstactId {

    private static final long serialVersionUID = 3796627473517044542L;

    private String type;

    @Convert(converter = MoneyConverter.class)
    private Money price;

}