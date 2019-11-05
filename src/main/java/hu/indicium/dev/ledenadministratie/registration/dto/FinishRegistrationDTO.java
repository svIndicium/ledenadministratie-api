package hu.indicium.dev.ledenadministratie.registration.dto;

public class FinishRegistrationDTO {
    private Long registrationId;

    private String comment;

    private boolean approved;

    public FinishRegistrationDTO(Long registrationId) {
        this.registrationId = registrationId;
        this.approved = true;
    }

    public FinishRegistrationDTO(Long registrationId, String comment) {
        this.registrationId = registrationId;
        this.comment = comment;
        this.approved = false;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
