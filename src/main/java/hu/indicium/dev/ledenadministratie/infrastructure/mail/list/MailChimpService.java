package hu.indicium.dev.ledenadministratie.infrastructure.mail.list;

import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailListType;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.list.requests.AddMailingListMemberRequest;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

@Service
public class MailChimpService implements MailListService {

    private final SettingService settingService;

    private final RestTemplate restTemplate;

    private final MailChimpMailListIdFactory mailChimpMailListIdFactory;

    public MailChimpService(SettingService settingService, RestTemplate restTemplate) {
        this.settingService = settingService;
        this.restTemplate = restTemplate;
        this.mailChimpMailListIdFactory = new MailChimpMailListIdFactory(settingService);
    }

    @Override
    public void addMailAddressToMailingList(MailAddress mailAddress, Name name, MailListType mailListType) {
        String mailListId = mailChimpMailListIdFactory.getListId(mailListType);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBasicAuth(settingService.getValueByKey("MAILCHIMP_USERNAME"), settingService.getValueByKey("MAILCHIMP_API_KEY"));
        HttpEntity<AddMailingListMemberRequest> httpEntity = new HttpEntity<>(new AddMailingListMemberRequest(mailAddress, name), httpHeaders);
        restTemplate.postForEntity("https://" + settingService.getValueByKey("MAILCHIMP_REGION") + ".api.mailchimp.com/3.0/lists/" + mailListId + "/members", httpEntity, String.class);
    }

    @Override
    public void removeMailAddressToMailingList(MailAddress mailAddress, Name name, MailListType mailListType) {
        String mailListId = mailChimpMailListIdFactory.getListId(mailListType);
        HttpHeaders httpHeaders = new HttpHeaders();
        String emailHash = DigestUtils.md5DigestAsHex(mailAddress.getAddress().getBytes());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBasicAuth(settingService.getValueByKey("MAILCHIMP_USERNAME"), settingService.getValueByKey("MAILCHIMP_API_KEY"));
        HttpEntity<AddMailingListMemberRequest> httpEntity = new HttpEntity<>(new AddMailingListMemberRequest(mailAddress, name), httpHeaders);
        restTemplate.delete("https://" + settingService.getValueByKey("MAILCHIMP_REGION") + ".api.mailchimp.com/3.0/lists/" + mailListId + "/members/" + emailHash, httpEntity, String.class);
    }
}
