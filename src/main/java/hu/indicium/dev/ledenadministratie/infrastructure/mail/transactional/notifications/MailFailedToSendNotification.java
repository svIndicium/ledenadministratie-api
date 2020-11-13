package hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional.notifications;

import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.Notification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailFailedToSendNotification implements Notification {

    private final String mailAddress;

    private final MailType mailType;

    private final String name;

    private final String reason;

    @Override
    public String getMessage() {
        return String.format("Failed to send mail (%s) to %s (%s). Reason: %s", mailType, name, mailAddress, reason);
    }
}
