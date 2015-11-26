package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.entity.User;

@Transactional
public interface UserRepository extends JpaRepository<User,Long>{
  User findByLogin(String login);
}
