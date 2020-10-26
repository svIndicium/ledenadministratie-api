package hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional;

import com.sendgrid.Response;

public class MailFailedToSendException extends RuntimeException {
    public MailFailedToSendException(Response response) {
        super(response.getBody());
    }
}
