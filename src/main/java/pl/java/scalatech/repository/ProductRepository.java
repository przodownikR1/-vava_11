package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.entity.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{

}
