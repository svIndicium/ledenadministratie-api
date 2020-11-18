package hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress;

import hu.indicium.dev.ledenadministratie.domain.AssertionConcern;
import hu.indicium.dev.ledenadministratie.domain.DomainEventPublisher;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MailAddress extends AssertionConcern {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, updatable = false)
    private String address;

    @Column(unique = true)
    private String verificationToken;

    @Column
    private Date verificationRequestedAt;

    @Column
    private Date verifiedAt;

    @Column
    private boolean receivesNewsletter;

    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    private Member member;

    @OneToOne(mappedBy = "mailAddress")
    private Registration registration;

    public MailAddress(String address, boolean receivesNewsletter) {
        this.setAddress(address);
        this.setReceivesNewsletter(receivesNewsletter);
        this.createdAt = new Date();
        this.requestVerification(verificationToken);
    }

    public void requestVerification(String verificationToken) {
        if (this.isVerified()) {
            throw new MailAddressAlreadyVerifiedException(this);
        }
        this.setVerificationRequestedAt(new Date());
        this.generateVerificationToken();
        DomainEventPublisher.instance()
                .publish(new MailAddressVerificationRequested(getAddress(), verificationToken));
    }

    public void verify() {
        if (this.isVerified()) {
            throw new MailAddressAlreadyVerifiedException(this);
        }
        this.setVerifiedAt(new Date());
        DomainEventPublisher.instance()
                .publish(new MailAddressVerified(getAddress(), getVerifiedAt()));
    }

    public boolean isVerified() {
        return this.verifiedAt != null;
    }

    private void generateVerificationToken() {
        this.setVerificationToken(Util.randomAlphaNumeric(10).toUpperCase());
    }

    public void setId(UUID id) {
        assertArgumentNotNull(id, "An ID for the mail address must be provided");

        this.id = id;
    }

    public void setAddress(String address) {
        assertArgumentNotNull(address, "Mail address must be provided");

        this.address = address;
    }

    public void setVerificationToken(String verificationToken) {
        assertArgumentNotNull(verificationToken, "A verification token for the mail address must be provided");

        this.verificationToken = verificationToken;
    }

    public void setVerificationRequestedAt(Date verificationRequestedAt) {
        assertArgumentNotNull(verificationRequestedAt, "A date for the mail verification must be provided");

        this.verificationRequestedAt = verificationRequestedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        assertArgumentNotNull(verifiedAt, "A date when the mail address was verified must be provided");

        this.verifiedAt = verifiedAt;
    }

    public void setReceivesNewsletter(boolean receivesNewsletter) {
        this.receivesNewsletter = receivesNewsletter;
    }

    public void setMember(Member member) {
        assertArgumentNotNull(member, "A member must be provided");
        this.member = member;
    }

    public void setRegistration(Registration registration) {
        assertArgumentNotNull(registration, "A registration must be provided");
        this.registration = registration;
    }
}
