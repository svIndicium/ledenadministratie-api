package hu.indicium.dev.lit.config;

import hu.indicium.dev.lit.user.UserServiceInterface;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    private final UserServiceInterface userService;

    public MethodSecurityConfig(UserServiceInterface userService) {
        this.userService = userService;
    }

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        final CustomMethodSecurityExpressionHandler expressionHandler = new CustomMethodSecurityExpressionHandler(userService);
        expressionHandler.setPermissionEvaluator(new DenyAllPermissionEvaluator());
        return expressionHandler;
    }

}
