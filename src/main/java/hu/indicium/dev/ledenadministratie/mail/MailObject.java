package hu.indicium.dev.ledenadministratie.mail;

import java.util.Date;

public interface MailObject {

    String getMailAddress();

    void setMailAddress(String mailAddress);

    Date getVerifiedAt();

    void verify();

    Date getVerificationRequestedAt();

    void setVerificationRequestedAt(Date verificationRequestedAt);

    boolean receivesNewsletter();

    void setReceivesNewsletter(boolean receivesNewsletter);

    String getVerificationToken();

    void setVerificationToken(String token);
}
