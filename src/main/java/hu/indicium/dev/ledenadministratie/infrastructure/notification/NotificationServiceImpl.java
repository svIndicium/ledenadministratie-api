package hu.indicium.dev.ledenadministratie.infrastructure.notification;

import hu.indicium.dev.ledenadministratie.infrastructure.notification.handlers.DiscordNotificationHandler;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.handlers.LoggingNotificationHandler;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final Collection<NotificationHandler> notificationHandlers;

    public NotificationServiceImpl(SettingService settingService) {
        this.notificationHandlers = new HashSet<>(
                Arrays.asList(
                        new DiscordNotificationHandler(settingService),
                        new LoggingNotificationHandler()
                )
        );
    }

    @Override
    public void sendNotification(Notification notification) {
        for (NotificationHandler notificationHandler : notificationHandlers) {
            notificationHandler.sendNotification(notification.getMessage());
        }
    }
}
