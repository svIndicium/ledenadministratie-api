package hu.indicium.dev.ledenadministratie.domain.model.user.member;

import hu.indicium.dev.ledenadministratie.domain.AssertionConcern;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "members")
@NoArgsConstructor
@Getter
@Setter
public class Member extends AssertionConcern {
    @EmbeddedId
    private MemberId memberId;

    @Embedded
    private MemberDetails memberDetails;

    @Embedded
    private ReviewDetails reviewDetails;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MailAddress> mailAddresses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Membership> memberships = new ArrayList<>();

    @UpdateTimestamp
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "registration_id")
    private Registration registration;

    private Member(Registration registration, MemberId memberId) {
        this.memberId = memberId;
        this.memberDetails = registration.getMemberDetails();
        this.reviewDetails = registration.getReviewDetails();
        this.registration = registration;
        this.addMailAddress(registration.getMailAddress());
        registration.setMember(this);
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

    public void setMemberId(MemberId memberId) {
        assertArgumentNotNull(memberId, "Member id must be given.");

        this.memberId = memberId;
    }

    public void setMemberDetails(MemberDetails memberDetails) {
        assertArgumentNotNull(memberDetails, "Member details must be given.");

        this.memberDetails = memberDetails;
    }

    public void setReviewDetails(ReviewDetails reviewDetails) {
        assertArgumentNotNull(reviewDetails, "Review details must be given.");

        this.reviewDetails = reviewDetails;
    }

    public void setMailAddresses(List<MailAddress> mailAddresses) {
        assertArgumentLength(mailAddresses, 1, 5, "Must have at least 1 mail address and maximal a total of 5 mail addresses.");

        this.mailAddresses = mailAddresses;
    }

    public void setMemberships(List<Membership> memberships) {
        assertArgumentLength(memberships, 1, 100, "Must have at least 1 membership and a maximal of 100 memberships.");

        this.memberships = memberships;
    }

    public void setRegistration(Registration registration) {
        assertArgumentNotNull(registration, "Registration must be given.");

        this.registration = registration;
    }
}
