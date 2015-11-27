package pl.java.scalatech.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@PasswordsEqualConstraint
public class User extends PKEntity implements UserDetails {
    private static final long serialVersionUID = -2181703844979860927L;

   // @NotNull
   // @Size(min = 2, max = 30)
    private String login;

   // @NotNull
   // @Size(min = 2, max = 50)
    private String lastName;

  // @NotNull
  //  @Size(min = 2, max = 50)
    private String firstName;

    @Transient
    private String fullName;

  //  @NotNull
 //   @Min(6)
    //@Column(nullable = false, length = 20)
    private String password;

    @Transient
    private String confirmPassword;

    private String email;

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "roleId") })
   // @Valid
    private List<Role> roles = new LinkedList<>();

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthoritiesSet = new HashSet<>(getRoles().size());
        List<String> roles = new ArrayList<>();
        for (Role role : getRoles()) {
            roles.add(role.getName());
        }
        List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
        grantedAuthoritiesSet.addAll(auths);

        return grantedAuthoritiesSet;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}