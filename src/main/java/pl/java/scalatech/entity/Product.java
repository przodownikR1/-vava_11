package pl.java.scalatech.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.java.scalatech.annotation.AmountFormat;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    //@NotNull
    @Size(min=2 , max=20)
    private String name;
    @NotNull

    @DecimalMin(value="10.00")
    @AmountFormat
    private BigDecimal price;

    @NotNull
    private Integer quantity;

    private boolean enable;

    @Transient
    private String common;


    @Transient
    private User owner = new User("test", "testNAme", "testFirst", "all", "passwd", "passwd1", "mail", true, Lists.newArrayList(new Role("USER","test")));


}
