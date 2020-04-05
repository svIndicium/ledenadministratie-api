package hu.indicium.dev.ledenadministratie.registration.events;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import org.springframework.context.ApplicationEvent;

public class RegistrationMailAddressVerified extends ApplicationEvent {

    private RegistrationDTO registrationDTO;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public RegistrationMailAddressVerified(Object source, RegistrationDTO mailAddress) {
        super(source);
        this.registrationDTO = mailAddress;
    }

    public RegistrationDTO getRegistrationDTO() {
        return registrationDTO;
    }
}
