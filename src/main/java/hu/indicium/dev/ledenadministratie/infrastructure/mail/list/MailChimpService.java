package hu.indicium.dev.ledenadministratie.infrastructure.mail.list;

import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class MailChimpService implements MailListService {

    private final RestTemplate restTemplate;

    @Override
    public void addMailAddressToMailingList(MailAddress mailAddress, String mailListId) {

    }

    @Override
    public void removeMailAddressToMailingList(MailAddress mailAddress, String mailListId) {

    }
}
