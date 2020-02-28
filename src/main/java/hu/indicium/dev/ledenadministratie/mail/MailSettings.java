package hu.indicium.dev.ledenadministratie.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mailchimp")
public class MailSettings {

    private String username;

    private String apiKey;

    private String region;

    private String memberListId;

    private String newsletterListId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getMemberListId() {
        return memberListId;
    }

    public void setMemberListId(String memberListId) {
        this.memberListId = memberListId;
    }

    public String getNewsletterListId() {
        return newsletterListId;
    }

    public void setNewsletterListId(String newsletterListId) {
        this.newsletterListId = newsletterListId;
    }
}
