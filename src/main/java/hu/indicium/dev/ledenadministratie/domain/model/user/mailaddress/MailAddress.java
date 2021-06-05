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

    @Column
    private boolean receivesNewsletter;

    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne
    private Member member;

    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    public MailAddress(String address, boolean receivesNewsletter) {
        this.setAddress(address);
        this.setReceivesNewsletter(receivesNewsletter);
        this.createdAt = new Date();
    }

    public void setId(UUID id) {
        assertArgumentNotNull(id, "An ID for the mail address must be provided");

        this.id = id;
    }

    public void setAddress(String address) {
        assertArgumentNotNull(address, "Mail address must be provided");

        this.address = address;
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
