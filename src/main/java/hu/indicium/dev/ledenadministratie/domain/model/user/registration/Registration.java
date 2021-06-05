package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import hu.indicium.dev.ledenadministratie.domain.AssertionConcern;
import hu.indicium.dev.ledenadministratie.domain.DomainEventPublisher;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Registration extends AssertionConcern {
    @EmbeddedId
    private RegistrationId registrationId;

    @Embedded
    private MemberDetails memberDetails;

    @Embedded
    private ReviewDetails reviewDetails;

    @Column(name = "review_status")
    private ReviewStatus reviewStatus;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "registration")
    private MailAddress mailAddress;

    @OneToOne(mappedBy = "registration")
    private Member member;

    public Registration(RegistrationId registrationId, MemberDetails memberDetails, MailAddress mailAddress) {
        this.setRegistrationId(registrationId);
        this.setMemberDetails(memberDetails);
        this.setMailAddress(mailAddress);
        this.setReviewStatus(ReviewStatus.PENDING);
        DomainEventPublisher.instance()
                .publish(new RegistrationCreated(this));
    }

    public void approve(String reviewedBy) {
        if (reviewStatus != ReviewStatus.PENDING) {
            throw new RegistrationAlreadyReviewedException(this);
        }
//      TODO: Check Keycloak if the mailaddress is verified
        this.setReviewDetails(ReviewDetails.approve(reviewedBy));
        this.setReviewStatus(ReviewStatus.APPROVED);
        DomainEventPublisher.instance()
                .publish(new RegistrationApproved(this));
    }

    public void deny(String reviewedBy, String comment) {
        if (reviewStatus != ReviewStatus.PENDING) {
            throw new RegistrationAlreadyReviewedException(this);
        }
        this.setReviewDetails(ReviewDetails.deny(reviewedBy, comment));
        this.setReviewStatus(ReviewStatus.DENIED);
    }

    public void setMember(Member member) {
        assertArgumentNotNull(member, "Member must not be null.");

        this.member = member;
    }

    public void setRegistrationId(RegistrationId registrationId) {
        assertArgumentNotNull(registrationId, "Member must not be null.");

        this.registrationId = registrationId;
    }

    public void setMemberDetails(MemberDetails memberDetails) {
        assertArgumentNotNull(memberDetails, "Memberdetails must not be null.");

        this.memberDetails = memberDetails;
    }

    public void setReviewDetails(ReviewDetails reviewDetails) {
        assertArgumentNotNull(reviewDetails, "Review details must not be null.");

        this.reviewDetails = reviewDetails;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        assertArgumentNotNull(reviewStatus, "Review status must not be null.");

        this.reviewStatus = reviewStatus;
    }

    public void setMailAddress(MailAddress mailAddress) {
        assertArgumentNotNull(mailAddress, "Mail address must not be null.");

        mailAddress.setRegistration(this);
        this.mailAddress = mailAddress;
    }
}
