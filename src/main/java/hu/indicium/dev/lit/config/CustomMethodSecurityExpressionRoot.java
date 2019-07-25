package hu.indicium.dev.lit.config;

import hu.indicium.dev.lit.user.UserServiceInterface;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Objects;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;

    private UserServiceInterface userService;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, UserServiceInterface userService) {
        super(authentication);
        this.userService = userService;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    public boolean hasPermission(String permission) {
        return getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority(permission));
    }

    public boolean isUser(Long userId) {
        return Objects.equals(userService.getUserByAuthUserId((String) getAuthentication().getPrincipal()).getId(), userId);
    }
}
