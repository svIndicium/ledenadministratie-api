package hu.indicium.dev.ledenadministratie.application.events.listeners;

import hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationCreated;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailService;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class RegistrationCreatedListener implements DomainEventSubscriber<RegistrationCreated> {

    private final MailService mailService;

    @Override
    public void handleEvent(RegistrationCreated registrationCreated) {
        Map<String, Object> mailParams = new HashMap<>();
        mailParams.put("token", registrationCreated.getMailAddress().getVerificationToken());
        mailService.sendMail(registrationCreated.getMailAddress(), registrationCreated.getName(), MailType.VERIFY_EMAIL, mailParams);
    }

    @Override
    public Class<RegistrationCreated> subscribedToEventType() {
        return RegistrationCreated.class;
    }
}
