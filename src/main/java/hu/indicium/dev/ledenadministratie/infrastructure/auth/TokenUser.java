package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import org.springframework.security.core.Authentication;

public class TokenUser implements User {

    private final String name;

    public TokenUser(Authentication authentication) {
        this.name = authentication.getName();
    }

    @Override
    public String getName() {
        return name;
    }
}
