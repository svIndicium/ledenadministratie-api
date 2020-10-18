package hu.indicium.dev.ledenadministratie.application.events;

import hu.indicium.dev.ledenadministratie.application.events.listeners.RegistrationCreatedListener;
import hu.indicium.dev.ledenadministratie.domain.DomainEventPublisher;
import hu.indicium.dev.ledenadministratie.domain.DomainEventSubscriber;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class EventListenerConfig implements CommandLineRunner {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void run(String... args) throws Exception {
        List<DomainEventSubscriber> subscribers = Arrays.asList(
                new RegistrationCreatedListener()
        );
        for (DomainEventSubscriber subscriber: subscribers) {
            DomainEventPublisher.instance().subscribe(subscriber);
        }
    }
}
