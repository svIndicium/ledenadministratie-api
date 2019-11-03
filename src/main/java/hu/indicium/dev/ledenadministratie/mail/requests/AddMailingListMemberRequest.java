package hu.indicium.dev.ledenadministratie.mail.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMailingListMemberRequest {

    @JsonProperty("email_address")
    private String emailAddress;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("merge_fields")
    private Map<String, String> mergeFields;

    public AddMailingListMemberRequest(MailEntryDTO mailEntryDTO) {
        this.emailAddress = mailEntryDTO.getEmail();
        this.status = mailEntryDTO.isToReceiveNewsletter() ? "subscribed" : "unsubscribed";
        this.tags = Arrays.asList("new", "newnew");
        this.mergeFields = new HashMap<>();
        this.mergeFields.put("FNAME", mailEntryDTO.getFirstName());
        this.mergeFields.put("LNAME", mailEntryDTO.getLastName());
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getMergeFields() {
        return mergeFields;
    }

    public void setMergeFields(Map<String, String> mergeFields) {
        this.mergeFields = mergeFields;
    }
}
