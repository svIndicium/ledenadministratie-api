package hu.indicium.dev.ledenadministratie.application.events;

import hu.indicium.dev.ledenadministratie.application.events.listeners.MemberCreatedListener;
import hu.indicium.dev.ledenadministratie.application.events.listeners.RegistrationApprovedListener;
import hu.indicium.dev.ledenadministratie.application.events.listeners.RegistrationCreatedListener;
import hu.indicium.dev.ledenadministratie.application.service.MemberService;
import hu.indicium.dev.ledenadministratie.domain.DomainEventPublisher;
import hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Configuration
public class EventListenerConfig implements CommandLineRunner {

    private final MailService mailService;

    private final MemberService memberService;

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void run(String... args) throws Exception {
        List<DomainEventSubscriber> subscribers = Arrays.asList(
                new RegistrationCreatedListener(mailService),
                new RegistrationApprovedListener(memberService),
                new MemberCreatedListener(mailService)
        );
        for (DomainEventSubscriber subscriber : subscribers) {
            DomainEventPublisher.instance().subscribe(subscriber);
        }
    }
}
