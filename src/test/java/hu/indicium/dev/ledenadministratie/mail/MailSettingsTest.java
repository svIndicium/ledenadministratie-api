package hu.indicium.dev.ledenadministratie.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Mail Settings")
@TestPropertySource(properties = {
        "mailchimp.region=eu",
        "mailchimp.listId=test",
        "mailchimp.username=testUser",
        "mailchimp.apiKey=testKey"
})
class MailSettingsTest {

    @Autowired
    private MailSettings mailSettings;

    @Test
    void getUsername() {
        assertThat(mailSettings.getUsername()).isEqualTo("testUser");
    }

    @Test
    void getApiKey() {
        assertThat(mailSettings.getApiKey()).isEqualTo("testKey");
    }

    @Test
    void getRegion() {
        assertThat(mailSettings.getRegion()).isEqualTo("eu");
    }

    @Test
    void getListId() {
        assertThat(mailSettings.getListId()).isEqualTo("test");
    }
}