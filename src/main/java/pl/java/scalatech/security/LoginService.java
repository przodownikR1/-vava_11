package pl.java.scalatech.security;

public interface LoginService {

    LoginStatus getStatus();

    LoginStatus login(String username, String password);
}