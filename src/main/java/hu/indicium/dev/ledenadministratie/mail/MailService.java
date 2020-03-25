package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;

public interface MailService {
    MailObject sendVerificationMail(MailObject mailObject, MailVerificationDTO mailVerificationDTO);

    void verifyMail(String mail, String token);

    boolean isMailAddressAlreadyVerified(String mailAddress);
}
