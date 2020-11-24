package hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional.notifications.MailFailedToSendNotification;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional.notifications.MailSentNotification;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.NotificationService;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@AllArgsConstructor
@Service
public class SendGridService implements TransactionalMailService {

    private final SettingService settingService;

    private final NotificationService notificationService;

    private final SendGridTemplateFactory sendGridTemplateFactory;

    public void sendMail(MailAddress mailAddress, Name name, MailType mailType, Map<String, Object> params) {
        String templateId = sendGridTemplateFactory.fromMailType(mailType);
        Email from = new Email("secretaris@joostlek.dev", "Secretaris Indicium");
        Email to = new Email(mailAddress.getAddress(), name.getFullName());
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setReplyTo(from);
        mail.setTemplateId(templateId);
        Personalization personalization = new Personalization();
        personalization.addTo(to);
        personalization.addDynamicTemplateData("FNAME", name.getFirstName());
        personalization.addDynamicTemplateData("LNAME", name.getFullLastName());
        personalization.addDynamicTemplateData("MAIL_ADDRESS", mailAddress.getAddress());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
        }
        mail.addPersonalization(personalization);
        SendGrid sg = new SendGrid(settingService.getValueByKey("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() != 202) {
                throw new MailFailedToSendException(response);
            }
            notificationService.sendNotification(new MailSentNotification(mailAddress.getAddress(), mailType, name.getFullName()));
        } catch (IOException | MailFailedToSendException ex) {
            notificationService.sendNotification(new MailFailedToSendNotification(mailAddress.getAddress(), mailType, name.getFullName(), ex.getMessage()));
        }
    }
}
