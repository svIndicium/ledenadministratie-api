package hu.indicium.dev.ledenadministratie.domain;

import java.util.Date;

public interface DomainEvent {

    int eventVersion();

    Date occurredOn();
}
