package hu.indicium.dev.lit.user.requests;

import javax.validation.constraints.NotEmpty;

public class StartSignUpRequest {
    @NotEmpty(message = "{constraints.NotEmpty.message}")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
