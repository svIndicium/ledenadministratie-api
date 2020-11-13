package hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional.notifications;

import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.Notification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailSentNotification implements Notification {

    private final String mailAddress;

    private final MailType mailType;

    private final String name;

    @Override
    public String getMessage() {
        return String.format("Sent mail (%s) to %s (%s).", mailType, name, mailAddress);
    }
}
