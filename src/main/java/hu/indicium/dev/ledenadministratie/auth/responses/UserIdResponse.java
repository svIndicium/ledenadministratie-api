package hu.indicium.dev.ledenadministratie.auth.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserIdResponse {
    @JsonProperty("user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
