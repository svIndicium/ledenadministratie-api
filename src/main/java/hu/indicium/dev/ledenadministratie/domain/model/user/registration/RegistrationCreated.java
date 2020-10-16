package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import lombok.Getter;

import java.util.Date;

@Getter
public class RegistrationCreated implements DomainEvent {

    private final String firstName;

    private final String middleName;

    private final String lastName;

    private final String mailAddress;

    private final boolean receivingNewsletter;

    private final String phoneNumber;

    private final Date dateOfBirth;

    private final Date createdAt;

    private final StudyTypeId studyTypeId;

    private final int eventVersion;

    public RegistrationCreated(Registration registration) {
        this.firstName = registration.getMemberDetails().getName().getFirstName();
        this.middleName = registration.getMemberDetails().getName().getMiddleName();
        this.lastName = registration.getMemberDetails().getName().getLastName();
        this.mailAddress = registration.getMailAddress().getAddress();
        this.receivingNewsletter = registration.getMailAddress().isReceivesNewsletter();
        this.phoneNumber = registration.getMemberDetails().getPhoneNumber();
        this.dateOfBirth = registration.getMemberDetails().getDateOfBirth();
        this.createdAt = registration.getMemberDetails().getCreatedAt();
        this.studyTypeId = registration.getMemberDetails().getStudyType().getStudyTypeId();
        this.eventVersion = 1;
    }

    @Override
    public int eventVersion() {
        return eventVersion;
    }

    @Override
    public Date occurredOn() {
        return createdAt;
    }
}
