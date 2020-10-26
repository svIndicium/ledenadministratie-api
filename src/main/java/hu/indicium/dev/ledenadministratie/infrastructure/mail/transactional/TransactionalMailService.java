package hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional;

import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;

import java.util.Map;

public interface TransactionalMailService {

    void sendMail(MailAddress mailAddress, Name name, MailType mailType, Map<String, Object> params);

}
