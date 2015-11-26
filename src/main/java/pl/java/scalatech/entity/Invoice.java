package pl.java.scalatech.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "simple_invoice")
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@NoArgsConstructor
public class Invoice extends PKEntity {

    private static final long serialVersionUID = -7305875286472112192L;

    @Column(name = "invoice_name", nullable = false)
    private String name;

    private boolean paid;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creataDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    @Transient
    private String description;

    @Min(10)
    @Max(1000)

    @NumberFormat(style = Style.CURRENCY)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)

    @Column(name = "invoice_type")
    private InvoiceType type;
    
     
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> products = new ArrayList<>();
    
    /*  @ManyToOne(cascade = CascadeType.ALL)
    private User createdBy;*/


}
