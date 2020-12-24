package hu.indicium.dev.ledenadministratie.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//@WithSecurityContext(factory = WithMockTokenSecurityContextFactory.class)
public @interface WithMockToken {
    String[] scope();

    boolean isAuthenticated() default true;
}
