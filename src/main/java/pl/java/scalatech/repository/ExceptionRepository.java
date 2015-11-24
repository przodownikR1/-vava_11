package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.entity.ExceptionEntity;
public interface ExceptionRepository extends JpaRepository<ExceptionEntity, Long>{

}
