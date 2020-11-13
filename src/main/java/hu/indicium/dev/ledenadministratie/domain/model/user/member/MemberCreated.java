package hu.indicium.dev.ledenadministratie.domain.model.user.member;

import hu.indicium.dev.ledenadministratie.domain.DomainEvent;
import lombok.Getter;

import java.util.Date;

@Getter
public class MemberCreated implements DomainEvent {

    private final Member member;

    private final Date occurredOn;

    public MemberCreated(Member member) {
        this.member = member;
        this.occurredOn = new Date();
    }

    @Override
    public int eventVersion() {
        return 0;
    }

    @Override
    public Date occurredOn() {
        return occurredOn;
    }
}
