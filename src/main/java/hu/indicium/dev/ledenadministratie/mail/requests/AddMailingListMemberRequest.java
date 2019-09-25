package hu.indicium.dev.ledenadministratie.mail.requests;

import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddMailingListMemberRequest {

    private String email_address;

    private String status;

    private List<String> tags;

    private Map<String, String> merge_fields;

    public AddMailingListMemberRequest(MailEntryDTO mailEntryDTO) {
        this.email_address = mailEntryDTO.getEmail();
        this.status = mailEntryDTO.isToReceiveNewsletter() ? "subscribed" : "unsubscribed";
        this.tags = Collections.singletonList("new");
        this.merge_fields = new HashMap<>();
        this.merge_fields.put("FNAME", mailEntryDTO.getFirstName());
        this.merge_fields.put("LNAME", mailEntryDTO.getLastName());
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
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

    public Map<String, String> getMerge_fields() {
        return merge_fields;
    }

    public void setMerge_fields(Map<String, String> merge_fields) {
        this.merge_fields = merge_fields;
    }
}
