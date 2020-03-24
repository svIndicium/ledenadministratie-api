package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;

public interface MailService {
    MailAbstract sendVerificationMail(MailAbstract mailAbstract, MailVerificationDTO mailVerificationDTO);

    void verifyMail(String mail, String token);
}
