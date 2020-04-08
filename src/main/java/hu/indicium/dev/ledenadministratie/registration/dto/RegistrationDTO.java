package hu.indicium.dev.ledenadministratie.registration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegistrationDTO {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String mailAddress;

    private String phoneNumber;

    private Date dateOfBirth;

    private Long studyTypeId;

    private boolean isToReceiveNewsletter;

    private Date created;

    private Date updated;

    private Date finalizedAt;

    private String finalizedBy;

    private boolean approved;

    private String comment;

    private Date verificationRequestedAt;

    private Date verifiedAt;

    @JsonIgnore
    private String verificationToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getStudyTypeId() {
        return studyTypeId;
    }

    public void setStudyTypeId(Long studyTypeId) {
        this.studyTypeId = studyTypeId;
    }

    public boolean isToReceiveNewsletter() {
        return isToReceiveNewsletter;
    }

    public void setToReceiveNewsletter(boolean isToReceiveNewsletter) {
        this.isToReceiveNewsletter = isToReceiveNewsletter;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getFinalizedAt() {
        return finalizedAt;
    }

    public void setFinalizedAt(Date finalizedAt) {
        this.finalizedAt = finalizedAt;
    }

    public String getFinalizedBy() {
        return finalizedBy;
    }

    public void setFinalizedBy(String finalizedBy) {
        this.finalizedBy = finalizedBy;
    }

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

    public Date getVerificationRequestedAt() {
        return verificationRequestedAt;
    }

    public void setVerificationRequestedAt(Date verificationRequestedAt) {
        this.verificationRequestedAt = verificationRequestedAt;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
}
