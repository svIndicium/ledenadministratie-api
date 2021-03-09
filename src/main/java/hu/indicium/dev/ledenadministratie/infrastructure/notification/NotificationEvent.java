package hu.indicium.dev.ledenadministratie.infrastructure.notification;

import hu.indicium.dev.ledenadministratie.infrastructure.messaging.Event;
import lombok.Getter;

@Getter
public class NotificationEvent extends Event {

    private final String message;

    public NotificationEvent(Notification notification) {
        super("notification");
        this.message = notification.getMessage();
    }
}
