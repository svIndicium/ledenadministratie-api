package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import hu.indicium.dev.ledenadministratie.domain.DomainEventPublisher;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddressNotVerifiedException;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Registration {
    @EmbeddedId
    private RegistrationId registrationId;

    @Embedded
    private MemberDetails memberDetails;

    @Embedded
    private ReviewDetails reviewDetails;

    @Column(name = "review_status")
    private ReviewStatus reviewStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mail_address_id")
    private MailAddress mailAddress;

    @OneToOne(mappedBy = "registration")
    private Member member;

    public Registration(RegistrationId registrationId, MemberDetails memberDetails, MailAddress mailAddress) {
        this.registrationId = registrationId;
        this.memberDetails = memberDetails;
        this.mailAddress = mailAddress;
        this.reviewStatus = ReviewStatus.PENDING;
        DomainEventPublisher.instance()
                .publish(new RegistrationCreated(this));
    }

    public Member approve(MemberId memberId, String reviewedBy) {
        if (reviewStatus != ReviewStatus.PENDING) {
            throw new RegistrationAlreadyReviewedException(this);
        }
        if (!mailAddress.isVerified()) {
            throw new MailAddressNotVerifiedException(mailAddress);
        }
        this.reviewDetails = ReviewDetails.approve(reviewedBy);
        this.reviewStatus = ReviewStatus.APPROVED;
        Member member = Member.fromRegistration(this, memberId);
        this.member = member;
        return member;
    }

    public void deny(String reviewedBy, String comment) {
        if (reviewStatus != ReviewStatus.PENDING) {
            throw new RegistrationAlreadyReviewedException(this);
        }
        this.reviewDetails = ReviewDetails.deny(reviewedBy, comment);
        this.reviewStatus = ReviewStatus.DENIED;
    }
}
