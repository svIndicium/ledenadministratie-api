package hu.indicium.dev.ledenadministratie.infrastructure.auth;

public class AuthException extends RuntimeException {
    public AuthException(String name) {
        super("Could not create user for " + name);
    }
}
