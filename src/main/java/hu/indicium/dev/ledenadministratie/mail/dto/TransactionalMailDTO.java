package hu.indicium.dev.ledenadministratie.mail.dto;

import java.util.HashMap;
import java.util.Map;

public class TransactionalMailDTO {
    private String firstName;

    private String lastName;

    private String mailAddress;

    private Map<String, Object> params = new HashMap<>();

    public TransactionalMailDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void set(String key, Object value) {
        this.params.put(key, value);
    }

    public Object get(String key) {
        return this.params.get(key);
    }
}
