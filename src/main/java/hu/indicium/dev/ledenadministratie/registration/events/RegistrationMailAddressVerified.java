package hu.indicium.dev.ledenadministratie.registration.events;

import org.springframework.context.ApplicationEvent;

public class RegistrationMailAddressVerified extends ApplicationEvent {

    private Long registrationId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RegistrationMailAddressVerified(Object source, Long registrationId) {
        super(source);
        this.registrationId = registrationId;
    }

    public Long getRegistrationId() {
        return registrationId;
    }
}
