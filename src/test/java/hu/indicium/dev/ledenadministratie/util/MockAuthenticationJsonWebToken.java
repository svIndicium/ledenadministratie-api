package hu.indicium.dev.ledenadministratie.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.spring.security.api.authentication.JwtAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MockAuthenticationJsonWebToken implements Authentication, JwtAuthentication {

    private DecodedJWT decodedJWT;

    private boolean authenticated;

    public MockAuthenticationJsonWebToken(String token, boolean isAuthenticated) {
        this.decodedJWT = JWT.decode(token);
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getToken() {
        return decodedJWT.getToken();
    }

    @Override
    public String getKeyId() {
        return decodedJWT.getKeyId();
    }

    @Override
    public Authentication verify(JWTVerifier verifier) throws JWTVerificationException {
        this.authenticated = true;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String scope = decodedJWT.getClaim("scope").asString();
        if (scope == null || scope.trim().isEmpty()) {
            return new ArrayList<>();
        }
        final String[] scopes = scope.split(" ");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>(scopes.length);
        for (String permission : scopes) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return decodedJWT.getToken();
    }

    @Override
    public Object getDetails() {
        return decodedJWT;
    }

    @Override
    public Object getPrincipal() {
        return decodedJWT.getSubject();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return decodedJWT.getSubject();
    }
}
