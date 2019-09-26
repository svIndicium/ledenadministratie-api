package hu.indicium.dev.ledenadministratie.user.requests;

import javax.validation.constraints.Email;
import java.util.Date;

public class UpdateUserRequest {

    private String firstName;

    private String middleName;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;

    private Date dateOfBirth;

    private Long studyTypeId;

    private boolean isToReceiveNewsletter;

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

    public boolean getToReceiveNewsletter() {
        return isToReceiveNewsletter;
    }
}
