package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class RegistrationDTO {
    private UUID registrationId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String phoneNumber;

    private Date dateOfBirth;

    private String mailAddress;

    private boolean receivingNewsletter;

    private UUID studyTypeId;

    private Date reviewedAt;

    private String reviewedBy;

    private String comment;

    private String memberId;

    private ReviewStatus reviewStatus;

    public RegistrationDTO(Registration registration) {
        this.registrationId = registration.getRegistrationId().getId();
        this.firstName = registration.getMemberDetails().getName().getFirstName();
        this.middleName = registration.getMemberDetails().getName().getMiddleName();
        this.lastName = registration.getMemberDetails().getName().getLastName();
        this.phoneNumber = registration.getMemberDetails().getPhoneNumber();
        this.dateOfBirth = registration.getMemberDetails().getDateOfBirth();
        this.mailAddress = registration.getMailAddress().getAddress();
        this.receivingNewsletter = registration.getMailAddress().isReceivesNewsletter();
        this.studyTypeId = registration.getMemberDetails().getStudyType().getStudyTypeId().getId();
        this.reviewStatus = registration.getReviewStatus();
        if (registration.getReviewStatus() != ReviewStatus.PENDING) {
            this.reviewedAt = registration.getReviewDetails().getReviewedAt();
            this.reviewedBy = registration.getReviewDetails().getReviewedBy();
            this.comment = registration.getReviewDetails().getComment();
        }
        if (registration.getMember() != null) {
            this.memberId = registration.getMember().getMemberId().getAuthId();
        }
    }
}
