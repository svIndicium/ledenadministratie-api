package hu.indicium.dev.ledenadministratie.user.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateUserRequest {

    @NotEmpty
    private String firstName;

    private String middleName;

    @NotEmpty
    private String lastName;

    @Email
    private String email;

    @NotEmpty
    private String phoneNumber;

    @NotNull
    private Date dateOfBirth;

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
