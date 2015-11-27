package pl.java.scalatech.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginStatus {

    private final boolean loggedIn;
    private final String username;

}