package pl.java.scalatech.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"password", "confirmPassword"})
// @PasswordsEqualConstraint
public class User extends PKEntity {
    private static final long serialVersionUID = -2181703844979860927L;

    @NotNull
    @Size(min = 2, max = 30)
    private String login;

    //@NotNull
    //@Size(min = 2, max = 50)
    private String lastName;

    // @NotNull
    // @Size(min = 2, max = 50)
    private String firstName;

    @Transient
    private String fullName;

    // @NotNull
    // @Min(6)
    // @Column(nullable = false, length = 20)
    @JsonIgnore
    private String password;

    @Transient
    private String confirmPassword;

    private String email;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
    // @Valid
    private List<Role> roles ;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        if (login == null) {
            if (other.login != null) return false;
        } else if (!login.equals(other.login)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        return result;
    }
    
    

}