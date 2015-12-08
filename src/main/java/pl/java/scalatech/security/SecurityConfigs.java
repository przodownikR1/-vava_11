package pl.java.scalatech.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import pl.java.scalatech.annotation.SecurityComponent;

@SecurityComponent

public final class SecurityConfigs {
    @Getter
    @Value("${security.authFailureMaxCount:3}")
    private int authFailureMaxCount;
    @Getter
    @Value("${security.passwdValidDays:10}")
    private int passwdValidDays;
    
}