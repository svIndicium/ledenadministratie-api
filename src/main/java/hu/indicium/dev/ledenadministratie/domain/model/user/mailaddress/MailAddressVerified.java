package hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;

import java.util.Date;

public class MailAddressVerified implements DomainEvent {

    private MemberId memberId;

    private String address;

    private Date verifiedAt;

    private final int eventVersion = 1;

    private final Date occurredOn;

    public MailAddressVerified(MemberId memberId, String address, Date verifiedAt) {
        this.memberId = memberId;
        this.address = address;
        this.verifiedAt = verifiedAt;
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
