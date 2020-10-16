package hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress;

import hu.indicium.dev.ledenadministratie.domain.DomainEventPublisher;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
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
public class MailAddress {

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

    public MailAddress(String address, boolean receivesNewsletter, String verificationToken) {
        this.setAddress(address);
        this.setReceivesNewsletter(receivesNewsletter);
        this.createdAt = new Date();
        this.requestVerification(verificationToken);
    }

    public void requestVerification(String verificationToken) {
        if (this.isVerified()) {
            throw new MailAddressAlreadyVerifiedException(this);
        }
        this.verificationRequestedAt = new Date();
        this.verificationToken = verificationToken;
        DomainEventPublisher.instance()
                .publish(new MailAddressVerificationRequested(getAddress(), verificationToken));
    }

    public void verify() {
        if (this.isVerified()) {
            throw new MailAddressAlreadyVerifiedException(this);
        }
        this.verifiedAt = new Date();
        DomainEventPublisher.instance()
                .publish(new MailAddressVerified(getAddress(), getVerifiedAt()));
    }

    public boolean isVerified() {
        return this.verifiedAt != null;
    }

}
