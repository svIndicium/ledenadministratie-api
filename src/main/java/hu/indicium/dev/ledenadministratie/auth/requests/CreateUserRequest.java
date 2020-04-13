package hu.indicium.dev.ledenadministratie.auth.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateUserRequest {
    private String email;

    @JsonProperty("email_verified")
    private boolean emailVerified;

    @JsonProperty("given_name")
    private String givenName;

    @JsonProperty("family_name")
    private String familyName;

    private String name;

    private String connection;

    private String password;

    public CreateUserRequest(String email, String givenName, String familyName, String password) {
        this.email = email;
        this.emailVerified = true;
        this.givenName = givenName;
        this.familyName = familyName;
        this.name = givenName + " " + familyName;
        this.connection = "Username-Password-Authentication";
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
