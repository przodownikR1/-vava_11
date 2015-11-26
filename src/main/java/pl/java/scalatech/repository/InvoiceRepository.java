package pl.java.scalatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.java.scalatech.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
