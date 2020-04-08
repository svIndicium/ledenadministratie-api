package hu.indicium.dev.ledenadministratie.user.events;

import org.springframework.context.ApplicationEvent;

public class MailAddressVerified extends ApplicationEvent {

    private Long userId;

    private Long mailAddressId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MailAddressVerified(Object source, Long userId, Long mailAddressId) {
        super(source);
        this.userId = userId;
        this.mailAddressId = mailAddressId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMailAddressId() {
        return mailAddressId;
    }
}
