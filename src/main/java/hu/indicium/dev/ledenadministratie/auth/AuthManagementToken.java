package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.util.Util;

import java.util.Date;
import java.util.List;

public class AuthManagementToken {
    private Date acquiredAt;

    private String accessToken;

    private String tokenType;

    private List<String> scopes;

    private int expiresIn;

    public Date getAcquiredAt() {
        return acquiredAt;
    }

    public void setAcquiredAt(Date acquiredAt) {
        this.acquiredAt = acquiredAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Date getExpiresAt() {
        return Util.addMinutesToDate(this.expiresIn, this.acquiredAt);
    }
}
