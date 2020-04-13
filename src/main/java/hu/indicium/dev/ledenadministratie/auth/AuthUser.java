package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.responses.UserInfoResponse;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class AuthUser {
    private String sub;

    private String givenName;

    private String familyName;

    private String nickname;

    private String name;

    private String pictureUrl;

    private String locale;

    private Date updatedAt;

    private String email;

    private boolean emailVerified;

    private Map<String, Object> appMetadata;

    public AuthUser() {
        // for modelmapper
    }

    public AuthUser(UserInfoResponse userInfoResponse) {
        this.sub = userInfoResponse.getSub();
        this.givenName = userInfoResponse.getGivenName();
        this.familyName = userInfoResponse.getFamilyName();
        this.nickname = userInfoResponse.getNickname();
        this.name = userInfoResponse.getName();
        this.pictureUrl = userInfoResponse.getPictureUrl();
        this.locale = userInfoResponse.getLocale();
        this.updatedAt = userInfoResponse.getUpdatedAt();
        this.email = userInfoResponse.getEmail();
        this.emailVerified = userInfoResponse.isEmailVerified();
        this.appMetadata = userInfoResponse.getAppMetadata();
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public Map<String, Object> getAppMetadata() {
        return appMetadata;
    }

    public void setAppMetadata(Map<String, Object> appMetadata) {
        this.appMetadata = appMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthUser authUser = (AuthUser) o;
        return emailVerified == authUser.emailVerified &&
                Objects.equals(sub, authUser.sub) &&
                Objects.equals(givenName, authUser.givenName) &&
                Objects.equals(familyName, authUser.familyName) &&
                Objects.equals(nickname, authUser.nickname) &&
                Objects.equals(name, authUser.name) &&
                Objects.equals(pictureUrl, authUser.pictureUrl) &&
                Objects.equals(locale, authUser.locale) &&
                Objects.equals(updatedAt, authUser.updatedAt) &&
                Objects.equals(email, authUser.email) &&
                Objects.equals(appMetadata, authUser.appMetadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sub, givenName, familyName, nickname, name, pictureUrl, locale, updatedAt, email, emailVerified, appMetadata);
    }
}
