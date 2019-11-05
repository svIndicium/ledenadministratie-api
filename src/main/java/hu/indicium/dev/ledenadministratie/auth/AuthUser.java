package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.requests.UserInfoRequest;

import java.util.Date;

public class AuthUser {
    private String sub;

    private String givenName;

    private String familyName;

    private String nickName;

    private String name;

    private String pictureUrl;

    private String locale;

    private Date updatedAt;

    private String email;

    private boolean emailVerified;

    public AuthUser(UserInfoRequest userInfoRequest) {
        this.sub = userInfoRequest.getSub();
        this.givenName = userInfoRequest.getGivenName();
        this.familyName = userInfoRequest.getFamilyName();
        this.nickName = userInfoRequest.getNickname();
        this.name = userInfoRequest.getName();
        this.pictureUrl = userInfoRequest.getPicture();
        this.locale = userInfoRequest.getLocale();
        this.updatedAt = userInfoRequest.getUpdatedAt();
        this.email = userInfoRequest.getEmail();
        this.emailVerified = userInfoRequest.isEmailVerified();
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
}
