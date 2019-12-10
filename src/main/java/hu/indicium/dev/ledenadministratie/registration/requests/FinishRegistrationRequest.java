package hu.indicium.dev.ledenadministratie.registration.requests;

import javax.validation.constraints.NotBlank;

public class FinishRegistrationRequest {
    @NotBlank
    private boolean approved;

    private String comment;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
