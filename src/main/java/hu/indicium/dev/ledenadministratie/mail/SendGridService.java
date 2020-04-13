package hu.indicium.dev.ledenadministratie.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import hu.indicium.dev.ledenadministratie.mail.dto.TransactionalMailDTO;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;


@Service
public class SendGridService implements TransactionalMailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final SettingService settingService;

    public SendGridService(SettingService settingService) {
        this.settingService = settingService;
    }

    @Override
    public void sendVerificationMail(TransactionalMailDTO transactionalMailDTO) {
        Email from = new Email(settingService.getValueByKey("SENDGRID_VERIFICATION_FROM_MAIL"), settingService.getValueByKey("SENDGRID_VERIFICATION_FROM_NAME"));
        String templateId = settingService.getValueByKey("SENDGRID_VERIFICATION_TEMPLATE");
        sendMail(from, templateId, transactionalMailDTO);
    }

    @Override
    public void sendPasswordResetMail(TransactionalMailDTO transactionalMailDTO) {
        Email from = new Email(settingService.getValueByKey("SENDGRID_PASSWORD_RESET_FROM_MAIL"), settingService.getValueByKey("SENDGRID_PASSWORD_RESET_FROM_NAME"));
        String templateId = settingService.getValueByKey("SENDGRID_PASSWORD_RESET_TEMPLATE");
        sendMail(from, templateId, transactionalMailDTO);
    }

    private void sendMail(Email from, String templateId, TransactionalMailDTO transactionalMailDTO) {
        Email to = new Email(transactionalMailDTO.getMailAddress(), transactionalMailDTO.getFirstName() + " " + transactionalMailDTO.getLastName());
        Mail mail = new Mail();
        mail.setFrom(from);
        mail.setReplyTo(from);
        mail.setTemplateId(templateId);
        Personalization personalization = new Personalization();
        personalization.addTo(to);
        personalization.addDynamicTemplateData("FNAME", transactionalMailDTO.getFirstName());
        personalization.addDynamicTemplateData("LNAME", transactionalMailDTO.getLastName());
        personalization.addDynamicTemplateData("MAIL_ADDRESS", transactionalMailDTO.getMailAddress());
        for (Map.Entry<String, Object> entry : transactionalMailDTO.getParams().entrySet()) {
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
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
