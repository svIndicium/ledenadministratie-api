package hu.indicium.dev.ledenadministratie.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("auth0")
public class AuthSettings {

    private String apiAudience;

    private String issuer;

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
}
