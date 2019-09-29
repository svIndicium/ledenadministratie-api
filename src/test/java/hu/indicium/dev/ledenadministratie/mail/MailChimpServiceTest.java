package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.util.MD5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.Optional;

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
    private MailSettings mailSettings;

    @Autowired
    private MailListService mailListService;

    @Test
    @DisplayName("Add user to mailing list")
    void shouldDoAPostRequestToMailChimp_whenAddTheUserToTheMailingList() {

        when(mailSettings.getListId()).thenReturn("test");
        when(mailSettings.getApiKey()).thenReturn("testApiKey");
        when(mailSettings.getUsername()).thenReturn("testUserName");
        when(mailSettings.getRegion()).thenReturn("eu");

        MailEntryDTO mailEntryDTO = new MailEntryDTO("John", "Doe", "john@doe.com", true);

        when(restTemplate.postForEntity(eq("https://eu.api.mailchimp.com/3.0/lists/test/members"), any(HttpHeaders.class), eq(String.class)))
                .thenReturn(ResponseEntity.of(Optional.of("worked!")));

        mailListService.addUserToMailingList(mailEntryDTO);

        verify(restTemplate, times(1)).postForEntity(eq("https://eu.api.mailchimp.com/3.0/lists/test/members"), any(), any());
    }

    @Test
    @DisplayName("Add user to mailing list")
    void shouldPatchTheUser_whenUpdateMailingListUser() {

        when(mailSettings.getListId()).thenReturn("test");
        when(mailSettings.getApiKey()).thenReturn("testApiKey");
        when(mailSettings.getUsername()).thenReturn("testUserName");
        when(mailSettings.getRegion()).thenReturn("eu");

        MailEntryDTO newMailEntry = new MailEntryDTO("John", "Doe", "john@doe.com", true);
        MailEntryDTO oldMailEntry = new MailEntryDTO("Johan", "Dough", "johan@dough.com", true);

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
        private MailSettings mailSettings;

        @Bean
        public MailListService mailListService() {
            return new MailChimpService(mailSettings, restTemplate);
        }
    }
}