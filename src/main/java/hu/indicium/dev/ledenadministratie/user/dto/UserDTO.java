package hu.indicium.dev.ledenadministratie.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserDTO {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Date dateOfBirth;

    private StudyTypeDTO studyType;

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

    public StudyTypeDTO getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyTypeDTO studyType) {
        this.studyType = studyType;
    }
}
