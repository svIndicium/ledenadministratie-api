package hu.indicium.dev.ledenadministratie.mail.requests;

public class MailVerificationRequest {
    private String address;

    private String token;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
