package pl.java.scalatech.service.unexceptedEx.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.java.scalatech.entity.ExceptionEntity;
import pl.java.scalatech.repository.ExceptionRepository;
import pl.java.scalatech.service.unexceptedEx.ExceptionLogService;
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExceptionLogServiceImpl implements ExceptionLogService{

    
    private @NonNull final ExceptionRepository exceptionRepository;
    
    @Override
    public ExceptionEntity save(ExceptionEntity ex) {
        return exceptionRepository.save(ex);
    }

}
