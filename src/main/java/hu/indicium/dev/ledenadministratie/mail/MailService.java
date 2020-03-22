package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.user.dto.MailDTO;

public interface MailService {
    MailDTO addMailAddress(MailEntryDTO mailEntryDTO);

    void addUserToMailingList(MailEntryDTO mailEntryDTO);

    void updateMailingListMember(MailEntryDTO oldEmail, MailEntryDTO newEmail);

    void addUserToNewsLetter(MailEntryDTO mailEntryDTO);

    void removeUserFromNewsLetter(MailEntryDTO mailEntryDTO);

    void sendVerificationMail(MailEntryDTO mailEntryDTO);

    void requestMailVerification(Long mailId);

    void verifyMail(String token);
}
