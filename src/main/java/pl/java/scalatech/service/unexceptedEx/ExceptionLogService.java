package pl.java.scalatech.service.unexceptedEx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.entity.ExceptionEntity;
import pl.java.scalatech.repository.ProductRepository;

public interface ExceptionLogService {
    
    ExceptionEntity save(ExceptionEntity ex);

}
