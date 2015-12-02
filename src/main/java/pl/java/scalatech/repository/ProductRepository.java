package pl.java.scalatech.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.java.scalatech.entity.Product;
import pl.java.scalatech.entity.User;


@Transactional(propagation=Propagation.REQUIRES_NEW,noRollbackFor=IllegalArgumentException.class)
public interface ProductRepository  extends JpaRepository<Product, Long>{
//
    List<Product> findByOwner(User user);

    @Override
    //@PostAuthorize("hasPermission(returnObject,'read')")
   // @PostAuthorize("hasPermission(returnObject?.owner?.id == principal?.id )")
    Product findOne(Long id);

}
