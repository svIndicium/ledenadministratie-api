package hu.indicium.dev.ledenadministratie.auth.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserResponse {
    @JsonProperty("user_id")
    private String userId;

    private String email;

    @JsonProperty("email_verfied")
    private boolean emailVerfied;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerfied() {
        return emailVerfied;
    }

    public void setEmailVerfied(boolean emailVerfied) {
        this.emailVerfied = emailVerfied;
    }
}
