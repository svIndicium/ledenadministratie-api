package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class RegistrationDTO {
    private UUID registrationId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String phoneNumber;

    private Date dateOfBirth;

    private String mailAddress;

    private boolean receivingNewsletter;

    private UUID studyTypeId;

    public RegistrationDTO(Registration registration) {
        this.registrationId = registration.getRegistrationId().getId();
        this.firstName = registration.getMemberDetails().getName().getFirstName();
        this.middleName = registration.getMemberDetails().getName().getMiddleName();
        this.lastName = registration.getMemberDetails().getName().getLastName();
        this.phoneNumber = registration.getMemberDetails().getPhoneNumber();
        this.dateOfBirth = registration.getMemberDetails().getDateOfBirth();
        this.mailAddress = registration.getMailAddress().getAddress();
        this.receivingNewsletter = registration.getMailAddress().isReceivesNewsletter();
        this.studyTypeId = registration.getMemberDetails().getStudyType().getStudyTypeId().getId();
    }
}
