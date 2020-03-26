package hu.indicium.dev.ledenadministratie.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class SendGridService implements TransactionalMailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void sendVerificationMail(MailVerificationDTO mailVerificationDTO) {
        logger.info("I am triggered!");
        logger.info(String.format("Sending a mail to %s", mailVerificationDTO.getMailAddress()));
        logger.info(String.format("Firstname %s Last name %s", mailVerificationDTO.getFirstName(), mailVerificationDTO.getLastName()));
        logger.info(String.format("The token is %s", mailVerificationDTO.getToken()));
        Email from = new Email("secretaris@indicium.hu", "Secretaris Indicium");
        Email to = new Email(mailVerificationDTO.getMailAddress(), mailVerificationDTO.getFirstName() + " " + mailVerificationDTO.getLastName());
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setReplyTo(from);
        mail.setTemplateId("d-c60b9dff12414f4da0c6521d243801ee");
        Personalization personalization = new Personalization();
        personalization.addTo(to);
        personalization.addDynamicTemplateData("firstName", mailVerificationDTO.getFirstName());
        personalization.addDynamicTemplateData("lastName", mailVerificationDTO.getLastName());
        personalization.addDynamicTemplateData("token", mailVerificationDTO.getToken());
        mail.addPersonalization(personalization);
        SendGrid sg = new SendGrid(System.getenv("SG_API"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
