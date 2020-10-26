package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Auth0ApiProvider {

    private final NotificationService notificationService;

    private final AuthSettings authSettings;

    public AuthAPI getAuthApi() {
        return new AuthAPI(authSettings.getIssuer(), authSettings.getClientId(), authSettings.getClientSecret());
    }

    public ManagementAPI getManagementAPI() {
        AuthAPI authAPI = getAuthApi();
        try {
            AuthRequest authRequest = authAPI.requestToken(authSettings.getIssuer() + "api/v2/");
            TokenHolder tokenHolder = authRequest.execute();
            return new ManagementAPI(authSettings.getIssuer(), tokenHolder.getAccessToken());
        } catch (Auth0Exception auth0Exception) {
            notificationService.sendNotification(new Auth0ConnectionNotification(auth0Exception));
            throw new RuntimeException(auth0Exception.getMessage());
        }
    }
}
