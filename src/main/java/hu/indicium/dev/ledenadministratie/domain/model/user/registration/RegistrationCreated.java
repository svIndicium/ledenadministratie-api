package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import lombok.Getter;

import java.util.Date;

@Getter
public class RegistrationCreated implements DomainEvent {

    private final Name name;

    private final MailAddress mailAddress;

    private final String phoneNumber;

    private final Date dateOfBirth;

    private final Date createdAt;

    private final StudyTypeId studyTypeId;

    private final int eventVersion;

    public RegistrationCreated(Registration registration) {
        this.name = registration.getMemberDetails().getName();
        this.mailAddress = registration.getMailAddress();
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
