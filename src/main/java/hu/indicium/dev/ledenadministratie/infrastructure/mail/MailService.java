package hu.indicium.dev.ledenadministratie.infrastructure.mail;

import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional.MailType;

import java.util.Map;

public interface MailService {
    void sendMail(MailAddress mailAddress, Name name, MailType mailType, Map<String, Object> params);

    void addMailAddressToMailingList(MailAddress mailAddress, String mailListId);

    void removeMailAddressToMailingList(MailAddress mailAddress, String mailListId);
}
