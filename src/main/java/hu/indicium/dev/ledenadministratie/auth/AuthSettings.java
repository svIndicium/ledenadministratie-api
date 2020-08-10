package hu.indicium.dev.ledenadministratie.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("auth0")
public class AuthSettings {

    private String apiAudience;

    private String issuer;

    private String clientId;

    private String clientSecret;

    public String getApiAudience() {
        return apiAudience;
    }

    public void setApiAudience(String apiAudience) {
        this.apiAudience = apiAudience;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
