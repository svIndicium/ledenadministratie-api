package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.mail.requests.AddMailingListMemberRequest;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import hu.indicium.dev.ledenadministratie.util.MD5;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailChimpService implements MailListService {

    private final SettingService settingService;

    private final RestTemplate restTemplate;

    private final MD5 md5;

    public MailChimpService(RestTemplate restTemplate, SettingService settingService) {
        this.restTemplate = restTemplate;
        this.md5 = new MD5();
        this.settingService = settingService;
    }

    @Override
    public void addUserToMailingList(MailEntryDTO mailEntryDTO) {
        addUserToList(mailEntryDTO, settingService.getValueByKey("MAILCHIMP_MEMBER_LIST_ID"));
    }

    @Override
    public void updateMailingListMember(MailEntryDTO oldEmail, MailEntryDTO newEmail) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String email = md5.hash(oldEmail.getEmail().toLowerCase());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.add("X-HTTP-Method-Override", "PATCH");
        httpHeaders.setBasicAuth(settingService.getValueByKey("MAILCHIMP_USERNAME"), settingService.getValueByKey("MAILCHIMP_API_KEY"));
        HttpEntity<AddMailingListMemberRequest> httpEntity = new HttpEntity<>(new AddMailingListMemberRequest(newEmail), httpHeaders);
        restTemplate.postForEntity("https://" + settingService.getValueByKey("MAILCHIMP_REGION") + ".api.mailchimp.com/3.0/lists/" + settingService.getValueByKey("MAILCHIMP_MEMBER_LIST_ID") + "/members/" + email, httpEntity, String.class);
    }

    @Override
    public void addUserToNewsLetter(MailEntryDTO mailEntryDTO) {
        addUserToList(mailEntryDTO, settingService.getValueByKey("MAILCHIMP_NEWSLETTER_LIST_ID"));
    }

    @Override
    public void removeUserFromNewsLetter(MailEntryDTO mailEntryDTO) {
        archiveUser(mailEntryDTO, settingService.getValueByKey("MAILCHIMP_NEWSLETTER_LIST_ID"));
    }

    private void addUserToList(MailEntryDTO mailEntryDTO, String listId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBasicAuth(settingService.getValueByKey("MAILCHIMP_USERNAME"), settingService.getValueByKey("MAILCHIMP_API_KEY"));
        HttpEntity<AddMailingListMemberRequest> httpEntity = new HttpEntity<>(new AddMailingListMemberRequest(mailEntryDTO), httpHeaders);
        restTemplate.postForEntity("https://" + settingService.getValueByKey("MAILCHIMP_REGION") + ".api.mailchimp.com/3.0/lists/" + listId + "/members", httpEntity, String.class);
    }

    private void archiveUser(MailEntryDTO mailEntryDTO, String listId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String emailHash = md5.hash(mailEntryDTO.getEmail());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBasicAuth(settingService.getValueByKey("MAILCHIMP_USERNAME"), settingService.getValueByKey("MAILCHIMP_API_KEY"));
        HttpEntity<AddMailingListMemberRequest> httpEntity = new HttpEntity<>(new AddMailingListMemberRequest(mailEntryDTO), httpHeaders);
        restTemplate.delete("https://" + settingService.getValueByKey("MAILCHIMP_REGION") + ".api.mailchimp.com/3.0/lists/" + listId + "/members/" + emailHash, httpEntity, String.class);
    }
}
