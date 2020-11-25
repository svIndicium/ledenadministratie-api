package hu.indicium.dev.ledenadministratie.infrastructure.mail.transactional;

import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SendGridTemplateFactory {

    private final SettingService settingService;

    public String fromMailType(MailType mailType) {
        switch (mailType) {
            case VERIFY_EMAIL:
                return settingService.getValueByKey("SENDGRID_VERIFICATION_TEMPLATE");
            case RESET_PASSWORD:
                return settingService.getValueByKey("SENDGRID_PASSWORD_RESET_TEMPLATE");
            default:
                return "";
        }
    }
}
