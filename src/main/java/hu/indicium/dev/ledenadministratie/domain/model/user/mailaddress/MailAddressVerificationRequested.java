package hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import lombok.Getter;

import java.util.Date;

@Getter
public class MailAddressVerificationRequested implements DomainEvent {

    private String mailAddress;

    private String verificationToken;

    private final int eventVersion = 1;

    private final Date occurredOn;

    public MailAddressVerificationRequested(String mailAddress, String verificationToken) {
        this.mailAddress = mailAddress;
        this.verificationToken = verificationToken;
        this.occurredOn = new Date();
    }

    @Override
    public int eventVersion() {
        return eventVersion;
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
