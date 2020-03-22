package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.registration.Registration;
import hu.indicium.dev.ledenadministratie.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Mail {
    @Id
    @SequenceGenerator(name = "mail_id_generator", sequenceName = "mail_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_id_generator")
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column
    private boolean verified;

    @Column(unique = true)
    private String verificationToken;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Column
    private Date verifiedAt;

    @Column
    private Date verificationRequestedAt;

    @Column
    private boolean receivesNewsletter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Registration registration;

    public Mail() {
    }

    public Mail(String address, boolean receivesNewsletter) {
        this.address = address;
        this.receivesNewsletter = receivesNewsletter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Date getVerificationRequestedAt() {
        return verificationRequestedAt;
    }

    public void setVerificationRequestedAt(Date verificationRequestedAt) {
        this.verificationRequestedAt = verificationRequestedAt;
    }

    public boolean isReceivesNewsletter() {
        return receivesNewsletter;
    }

    public void setReceivesNewsletter(boolean receivesNewsletter) {
        this.receivesNewsletter = receivesNewsletter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}
