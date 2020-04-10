package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.mail.requests.AddMailingListMemberRequest;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import hu.indicium.dev.ledenadministratie.util.MD5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("MailChimp Service")
class MailChimpServiceTest {

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private SettingService settingService;

    @Autowired
    private MailListService mailListService;

    @BeforeEach
    void setUp() {
        when(settingService.getValueByKey("MAILCHIMP_MEMBER_LIST_ID")).thenReturn("test");
        when(settingService.getValueByKey("MAILCHIMP_NEWSLETTER_LIST_ID")).thenReturn("newsletter");
        when(settingService.getValueByKey("MAILCHIMP_API_KEY")).thenReturn("testApiKey");
        when(settingService.getValueByKey("MAILCHIMP_USERNAME")).thenReturn("testUserName");
        when(settingService.getValueByKey("MAILCHIMP_REGION")).thenReturn("eu");
    }

    @Test
    @DisplayName("Add user to mailing list")
    void shouldDoAPostRequestToMailChimp_whenAddTheUserToTheMailingList() {

        ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        MailEntryDTO mailEntryDTO = new MailEntryDTO("John", "Doe", "john@doe.com");

        when(restTemplate.postForEntity(eq("https://eu.api.mailchimp.com/3.0/lists/test/members"), httpEntityArgumentCaptor.capture(), eq(String.class)))
                .thenReturn(ResponseEntity.of(Optional.of("worked!")));

        mailListService.addUserToMailingList(mailEntryDTO);

        HttpEntity httpEntity = httpEntityArgumentCaptor.getValue();
        AddMailingListMemberRequest addMailingListMemberRequest = (AddMailingListMemberRequest) httpEntity.getBody();
        assertThat(addMailingListMemberRequest).isNotNull();
        assertThat(addMailingListMemberRequest.getEmailAddress()).isEqualTo(mailEntryDTO.getEmail());
        assertThat(addMailingListMemberRequest.getStatus()).isEqualTo("subscribed");
        assertThat(addMailingListMemberRequest.getMergeFields()).containsKeys("FNAME");
        assertThat(addMailingListMemberRequest.getMergeFields()).containsKeys("LNAME");


        verify(restTemplate, times(1)).postForEntity(eq("https://eu.api.mailchimp.com/3.0/lists/test/members"), any(), any());
    }

    @Test
    @DisplayName("Add user to mailing list")
    void shouldPatchTheUser_whenUpdateMailingListUser() {

        MailEntryDTO newMailEntry = new MailEntryDTO("John", "Doe", "john@doe.com");
        MailEntryDTO oldMailEntry = new MailEntryDTO("Johan", "Dough", "johan@dough.com");

        MD5 md5crypt = new MD5();
        String md5 = md5crypt.hash(oldMailEntry.getEmail());

        when(restTemplate.postForEntity(eq("https://eu.api.mailchimp.com/3.0/lists/test/members/" + md5), any(HttpHeaders.class), eq(String.class)))
                .thenReturn(ResponseEntity.of(Optional.of("worked!")));

        mailListService.updateMailingListMember(oldMailEntry, newMailEntry);

        verify(restTemplate, times(1)).postForEntity(eq("https://eu.api.mailchimp.com/3.0/lists/test/members/" + md5), any(), any());
    }

    @TestConfiguration
    static class MailChimpServiceTestContextConfiguration {
        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private SettingService settingService;

        @Bean
        public MailListService mailListService() {
            return new MailChimpService(restTemplate, settingService);
        }
    }
}