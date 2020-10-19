package hu.indicium.dev.ledenadministratie.mail.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMailingListMemberRequest {

    @JsonProperty("email_address")
    private final String emailAddress;

    @JsonProperty("status")
    private final String status;

    @JsonProperty("tags")
    private final List<String> tags;

    @JsonProperty("merge_fields")
    private final Map<String, String> mergeFields;

    public AddMailingListMemberRequest(MailEntryDTO mailEntryDTO) {
        this.emailAddress = mailEntryDTO.getEmail();
        this.status = "subscribed";
        this.mergeFields = new HashMap<>();
        this.tags = Arrays.asList("newnewnew");
        this.mergeFields.put("FNAME", mailEntryDTO.getFirstName());
        this.mergeFields.put("LNAME", mailEntryDTO.getLastName());
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getTags() {
        return tags;
    }

    public Map<String, String> getMergeFields() {
        return mergeFields;
    }
}
