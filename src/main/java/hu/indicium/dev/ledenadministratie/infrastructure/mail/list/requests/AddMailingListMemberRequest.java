package hu.indicium.dev.ledenadministratie.infrastructure.mail.list.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;

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

    public AddMailingListMemberRequest(MailAddress mailAddress, Name name) {
        this.emailAddress = mailAddress.getAddress();
        this.status = "subscribed";
        this.mergeFields = new HashMap<>();
        this.tags = Arrays.asList("newnewnewnew");
        this.mergeFields.put("FNAME", name.getFirstName());
        this.mergeFields.put("LNAME", name.getLastName());
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
