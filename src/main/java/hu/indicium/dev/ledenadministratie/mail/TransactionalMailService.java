package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;

public interface TransactionalMailService {
    void sendVerificationMail(MailVerificationDTO mailVerificationDTO);
}
