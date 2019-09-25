package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;

public interface MailListService {
    void addUserToMailingList(MailEntryDTO mailEntryDTO);

    void updateMailingListMember(MailEntryDTO oldEmail, MailEntryDTO newEmail);
}
