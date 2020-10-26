package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import com.auth0.exception.Auth0Exception;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.Notification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Auth0ConnectionNotification implements Notification {

    private final Auth0Exception auth0Exception;

    @Override
    public String getMessage() {
        return String.format("Could not connect to auth0: %s", auth0Exception.getMessage());
    }
}
