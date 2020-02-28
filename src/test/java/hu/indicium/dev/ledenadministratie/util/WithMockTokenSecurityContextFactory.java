package hu.indicium.dev.ledenadministratie.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Date;

public class WithMockTokenSecurityContextFactory implements WithSecurityContextFactory<WithMockToken> {
    @Override
    public SecurityContext createSecurityContext(WithMockToken tokenAnnotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        String scopes = String.join(" ", tokenAnnotation.scope());

        String token = JWT.create()
                .withIssuer("https://indicium.eu.auth0.com/")
                .withSubject("test")
                .withAudience("https://lit.indicium.hu", "https://indicium.eu.auth0.com/userinfo")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 100000))
                .withClaim("scope", scopes)
                .sign(Algorithm.HMAC256("123456"));
        Authentication authentication = new MockAuthenticationJsonWebToken(token, tokenAnnotation.isAuthenticated());
        context.setAuthentication(authentication);
        return context;
    }
}
