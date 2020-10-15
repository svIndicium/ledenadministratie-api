package hu.indicium.dev.ledenadministratie.domain.model.user.member;

import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "members")
@NoArgsConstructor
@Getter
@Setter
public class Member {
    @EmbeddedId
    private MemberId memberId;

    @Embedded
    private MemberDetails memberDetails;

    @Embedded
    private ReviewDetails reviewDetails;

    @OneToMany(mappedBy = "member")
    private List<MailAddress> mailAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>();

    @UpdateTimestamp
    private Date updatedAt;

    @OneToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    private Member(Registration registration, MemberId memberId) {
        this.memberId = memberId;
        this.memberDetails = registration.getMemberDetails();
        this.reviewDetails = registration.getReviewDetails();
        this.registration = registration;
        this.addMailAddress(registration.getMailAddress());
    }

    public static Member fromRegistration(Registration registration, MemberId memberId) {
        return new Member(registration, memberId);
    }

    public void addMailAddress(MailAddress mailAddress) {
        mailAddress.setMember(this);
        mailAddresses.add(mailAddress);
    }

    public boolean isActive() {
        return this.memberships.stream().anyMatch(Membership::isActive);
    }
}
