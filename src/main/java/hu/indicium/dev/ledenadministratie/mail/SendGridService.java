package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class SendGridService implements TransactionalMailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sendVerificationMail(MailVerificationDTO mailVerificationDTO) {
        logger.info("I am triggered!");
        logger.info(String.format("Sending a mail to %s", mailVerificationDTO.getMailAddress()));
        logger.info(String.format("Firstname %s Last name %s", mailVerificationDTO.getFirstName(), mailVerificationDTO.getLastName()));
        logger.info(String.format("The token is %s", mailVerificationDTO.getToken()));
    }
}
