package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import lombok.Getter;

import java.util.Date;

@Getter
public class RegistrationCreated implements DomainEvent {

    private final Registration registration;

    private final Date createdAt;

    private final int eventVersion;

    public RegistrationCreated(Registration registration) {
        this.registration = registration;
        this.createdAt = registration.getMemberDetails().getCreatedAt();
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
