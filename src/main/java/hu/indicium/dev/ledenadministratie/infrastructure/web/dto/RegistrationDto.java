package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RegistrationDto {
    private UUID id;

    private MemberDetailsDto memberDetails;

    private ReviewDetailsDto reviewDetails;

    private String mailAddress;

    private boolean receivingNewsletter;

    private String memberId;

    private ReviewStatus reviewStatus;

    public RegistrationDto(Registration registration) {
        this.id = registration.getRegistrationId().getId();
        this.memberDetails = new MemberDetailsDto(registration.getMemberDetails());
        this.mailAddress = registration.getMailAddress().getAddress();
        this.receivingNewsletter = registration.getMailAddress().isReceivesNewsletter();
        this.reviewStatus = registration.getReviewStatus();
        if (registration.getReviewStatus() != ReviewStatus.PENDING) {
            this.reviewDetails = new ReviewDetailsDto(registration.getReviewDetails());
        }
        if (registration.getMember() != null) {
            this.memberId = registration.getMember().getMemberId().getAuthId();
        }
    }
}
