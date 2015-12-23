package pl.java.scalatech.service;

import org.springframework.stereotype.Service;

@Service
public class RateLimitService {

    public long incrementLimit(String remoteAddr) {
        return 0;
    }

}
