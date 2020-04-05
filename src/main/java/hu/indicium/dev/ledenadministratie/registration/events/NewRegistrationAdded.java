package hu.indicium.dev.ledenadministratie.registration.events;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import org.springframework.context.ApplicationEvent;

public class NewRegistrationAdded extends ApplicationEvent {

    private RegistrationDTO registrationDTO;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public NewRegistrationAdded(Object source, RegistrationDTO registrationDTO) {
        super(source);
        this.registrationDTO = registrationDTO;
    }

    public RegistrationDTO getRegistrationDTO() {
        return registrationDTO;
    }
}
