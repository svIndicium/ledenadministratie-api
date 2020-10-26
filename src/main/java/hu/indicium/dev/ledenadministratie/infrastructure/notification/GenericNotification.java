package hu.indicium.dev.ledenadministratie.infrastructure.notification;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenericNotification implements Notification {

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
