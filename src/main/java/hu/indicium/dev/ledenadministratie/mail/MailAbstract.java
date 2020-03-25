package hu.indicium.dev.ledenadministratie.mail;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public abstract class MailAbstract {

    @Column(nullable = false)
    private String mailAddress;

    @Column()
    private String verificationToken;

    @Column()
    private Date verificationRequestedAt;

    @Column()
    private Date verifiedAt;

    @Column()
    private boolean receivesNewsletter;

    public MailAbstract() {
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Date getVerificationRequestedAt() {
        return verificationRequestedAt;
    }

    public void setVerificationRequestedAt(Date verificationRequestedAt) {
        this.verificationRequestedAt = verificationRequestedAt;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public boolean isReceivesNewsletter() {
        return receivesNewsletter;
    }

    public void setReceivesNewsletter(boolean receivesNewsletter) {
        this.receivesNewsletter = receivesNewsletter;
    }
}
