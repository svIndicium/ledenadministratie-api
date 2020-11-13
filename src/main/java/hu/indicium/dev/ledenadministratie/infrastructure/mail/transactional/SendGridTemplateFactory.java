package hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional;

import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;

public class SendGridTemplateFactory {

    private SendGridTemplateFactory() {
    }

    public static String fromMailType(MailType mailType) {
        switch (mailType) {
            case VERIFY_EMAIL:
                return "d-c60b9dff12414f4da0c6521d243801ee";
            default:
                return "";
        }
    }
}
