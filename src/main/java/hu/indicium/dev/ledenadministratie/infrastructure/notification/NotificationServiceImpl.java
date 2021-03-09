package hu.indicium.dev.ledenadministratie.infrastructure.notification;

import hu.indicium.dev.ledenadministratie.infrastructure.messaging.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final EventService eventService;

    @Override
    public void sendNotification(Notification notification) {
        NotificationEvent notificationEvent = new NotificationEvent(notification);
        eventService.emitEvent(notificationEvent);
    }
}
