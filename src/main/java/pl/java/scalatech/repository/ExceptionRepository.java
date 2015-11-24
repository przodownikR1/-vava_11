package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.entity.ExceptionEntity;
@Transactional(noRollbackFor=IllegalArgumentException.class)
public interface ExceptionRepository extends JpaRepository<ExceptionEntity, Long>{

}
