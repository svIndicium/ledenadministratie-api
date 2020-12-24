package hu.indicium.dev.ledenadministratie.infrastructure.mail.list;

import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailListType;

public interface MailListService {

    void addMailAddressToMailingList(MailAddress mailAddress, Name name, MailListType mailListType);

    void removeMailAddressToMailingList(MailAddress mailAddress, Name name, MailListType mailListType);
}
