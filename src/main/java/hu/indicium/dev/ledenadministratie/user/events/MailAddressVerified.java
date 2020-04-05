package hu.indicium.dev.ledenadministratie.user.events;

import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import org.springframework.context.ApplicationEvent;

public class MailAddressVerified extends ApplicationEvent {

    private MailAddressDTO mailAddressDTO;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MailAddressVerified(Object source, MailAddressDTO mailAddress) {
        super(source);
        this.mailAddressDTO = mailAddress;
    }

    public MailAddressDTO getMailAddressDTO() {
        return mailAddressDTO;
    }
}
