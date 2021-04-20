package hu.indicium.dev.ledenadministratie.application.events.listeners;

import hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationCreated;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailService;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class RegistrationCreatedListener implements DomainEventSubscriber<RegistrationCreated> {

    private final AuthService authService;

    @Override
    public void handleEvent(RegistrationCreated registrationCreated) {
        authService.requestAccountSetup(registrationCreated.getRegistration().getRegistrationId());
    }

    @Override
    public Class<RegistrationCreated> subscribedToEventType() {
        return RegistrationCreated.class;
    }
}
