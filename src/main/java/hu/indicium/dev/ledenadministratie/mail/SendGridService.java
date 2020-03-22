package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import org.springframework.stereotype.Service;

@Service
public class SendGridService implements TransactionalMailService {
    @Override
    public void sendVerificationMail(MailVerificationDTO mailVerificationDTO) {

    }
}
