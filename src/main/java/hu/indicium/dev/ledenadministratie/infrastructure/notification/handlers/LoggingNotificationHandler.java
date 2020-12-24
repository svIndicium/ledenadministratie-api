package hu.indicium.dev.ledenadministratie.infrastructure.notification.handlers;

import hu.indicium.dev.ledenadministratie.infrastructure.notification.NotificationHandler;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class LoggingNotificationHandler implements NotificationHandler {
    @Override
    public void sendNotification(String message) {
        log.info(message);
    }
}
