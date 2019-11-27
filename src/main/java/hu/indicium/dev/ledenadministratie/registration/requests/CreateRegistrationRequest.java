package hu.indicium.dev.ledenadministratie.registration.requests;

import javax.validation.constraints.*;
import java.util.Date;

public class CreateRegistrationRequest {

    @NotBlank
    @NotNull
    private String firstName;

    private String middleName;

    @NotBlank
    @NotNull
    private String lastName;

    @Email
    @NotNull
    private String email;

    @NotEmpty
    @NotNull
    private String phoneNumber;

    @NotNull
    private Date dateOfBirth;

    @Positive
    @NotNull
    private Long studyTypeId;

    @NotNull
    private boolean isToReceiveNewsletter;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setToReceiveNewsletter(boolean toReceiveNewsletter) {
        isToReceiveNewsletter = toReceiveNewsletter;
    }
}
