package hu.indicium.dev.ledenadministratie.user.requests;

import javax.validation.constraints.*;
import java.util.Date;

public class UpdateUserRequest {

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

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getStudyTypeId() {
        return studyTypeId;
    }
}
