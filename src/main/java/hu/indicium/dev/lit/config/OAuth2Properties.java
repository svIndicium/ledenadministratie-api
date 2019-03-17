package hu.indicium.dev.lit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("indicium.oauth")
public class OAuth2Properties {
    private String checkTokenUrl;
    private String clientId;
    private String clientSecret;

    public String getCheckTokenUrl() {
        return checkTokenUrl;
    }

    public void setCheckTokenUrl(String checkTokenUrl) {
        this.checkTokenUrl = checkTokenUrl;
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
