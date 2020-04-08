package hu.indicium.dev.ledenadministratie.user.events;

import org.springframework.context.ApplicationEvent;

public class UserCreated extends ApplicationEvent {

    private Long userId;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public UserCreated(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
