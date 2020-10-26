package hu.indicium.dev.ledenadministratie.infrastructure.mail.list;

import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;

public interface MailListService {

    void addMailAddressToMailingList(MailAddress mailAddress, String mailListId);

    void removeMailAddressToMailingList(MailAddress mailAddress, String mailListId);
}
