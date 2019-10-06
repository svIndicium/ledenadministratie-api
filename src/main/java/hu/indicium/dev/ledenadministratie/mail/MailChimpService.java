package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.mail.requests.AddMailingListMemberRequest;
import hu.indicium.dev.ledenadministratie.util.MD5;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailChimpService implements MailListService {

    private final MailSettings mailSettings;

    private final RestTemplate restTemplate;

    private MD5 md5;

    public MailChimpService(MailSettings mailSettings, RestTemplate restTemplate) {
        this.mailSettings = mailSettings;
        this.restTemplate = restTemplate;
        this.md5 = new MD5();
    }

    @Override
    public void addUserToMailingList(MailEntryDTO mailEntryDTO) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBasicAuth(mailSettings.getUsername(), mailSettings.getApiKey());
        HttpEntity<AddMailingListMemberRequest> httpEntity = new HttpEntity<>(new AddMailingListMemberRequest(mailEntryDTO), httpHeaders);
        restTemplate.postForEntity("https://" + mailSettings.getRegion() + ".api.mailchimp.com/3.0/lists/" + mailSettings.getListId() + "/members", httpEntity, String.class);
    }

    @Override
    public void updateMailingListMember(MailEntryDTO oldEmail, MailEntryDTO newEmail) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String email = md5.hash(oldEmail.getEmail().toLowerCase());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.add("X-HTTP-Method-Override", "PATCH");
        httpHeaders.setBasicAuth(mailSettings.getUsername(), mailSettings.getApiKey());
        HttpEntity<AddMailingListMemberRequest> httpEntity = new HttpEntity<>(new AddMailingListMemberRequest(newEmail), httpHeaders);
        restTemplate.postForEntity("https://" + mailSettings.getRegion() + ".api.mailchimp.com/3.0/lists/" + mailSettings.getListId() + "/members/" + email, httpEntity, String.class);
    }
}
