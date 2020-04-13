package hu.indicium.dev.ledenadministratie.auth.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestPasswordChangeTicketRequest {
    @JsonProperty("user_id")
    private String userId;

    public RequestPasswordChangeTicketRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
