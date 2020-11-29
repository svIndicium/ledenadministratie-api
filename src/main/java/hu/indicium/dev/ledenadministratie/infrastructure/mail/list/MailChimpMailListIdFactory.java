package hu.indicium.dev.ledenadministratie.infrastructure.mail.list;

import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailListType;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MailChimpMailListIdFactory {
    private final SettingService settingService;

    public String getListId(MailListType mailListType) {
        switch (mailListType) {
            case GENERAL:
                return settingService.getValueByKey("MAILCHIMP_MEMBER_LIST_ID");
            case NEWSLETTER:
                return settingService.getValueByKey("MAILCHIMP_NEWSLETTER_LIST_ID");
            default:
                throw new RuntimeException("List id not configured");
        }
    }
}
