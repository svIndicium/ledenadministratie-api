package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MailAddress implements MailObject {

    @Id
    @GeneratedValue
    private Long id;

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

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public MailAddress() {
    }

    public MailAddress(String address, boolean receivesNewsletter) {
        this.setMailAddress(address);
        this.setReceivesNewsletter(receivesNewsletter);
    }

    public MailAddress(String mailAddress, String verificationToken, Date verificationRequestedAt, Date verifiedAt, boolean receivesNewsletter) {
        this.mailAddress = mailAddress;
        this.verificationToken = verificationToken;
        this.verificationRequestedAt = verificationRequestedAt;
        this.verifiedAt = verifiedAt;
        this.receivesNewsletter = receivesNewsletter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getMailAddress() {
        return mailAddress;
    }

    @Override
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    @Override
    public Date getVerifiedAt() {
        return verifiedAt;
    }

    @Override
    public void verify() {
        this.verifiedAt = new Date();
    }

    @Override
    public Date getVerificationRequestedAt() {
        return verificationRequestedAt;
    }

    @Override
    public void setVerificationRequestedAt(Date verificationRequestedAt) {
        this.verificationRequestedAt = verificationRequestedAt;
    }

    @Override
    public boolean receivesNewsletter() {
        return receivesNewsletter;
    }

    @Override
    public void setReceivesNewsletter(boolean receivesNewsletter) {
        this.receivesNewsletter = receivesNewsletter;
    }

    @Override
    public String getVerificationToken() {
        return verificationToken;
    }

    @Override
    public void setVerificationToken(String token) {
        this.verificationToken = token;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
