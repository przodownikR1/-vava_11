package pl.java.scalatech.entity;

import javax.persistence.Entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Builder
public class Role extends PKEntity {

    private static final long serialVersionUID = -804077594557972107L;
    private String name;
}
