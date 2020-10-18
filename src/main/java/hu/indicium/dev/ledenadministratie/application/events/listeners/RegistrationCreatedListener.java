package hu.indicium.dev.ledenadministratie.application.events.listeners;

import hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationCreated;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RegistrationCreatedListener implements DomainEventSubscriber<RegistrationCreated> {
    @Override
    public void handleEvent(RegistrationCreated aDomainEvent) {
        System.out.println(aDomainEvent);
    }

    @Override
    public Class<RegistrationCreated> subscribedToEventType() {
        return RegistrationCreated.class;
    }
}
