package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import lombok.Getter;

import java.util.Date;

@Getter
public class RegistrationApproved implements DomainEvent {

    private final Registration registration;

    private final int eventVersion;

    private final Date occurredOn;

    public RegistrationApproved(Registration registration) {
        this.registration = registration;
        this.eventVersion = 0;
        this.occurredOn = new Date();
    }

    @Override
    public int eventVersion() {
        return eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
