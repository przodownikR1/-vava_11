package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.entity.Role;
@Transactional
//@PreAuthorize("hasRole('ADMIN')")
public interface RoleRepository extends JpaRepository<Role, Long>{


}
