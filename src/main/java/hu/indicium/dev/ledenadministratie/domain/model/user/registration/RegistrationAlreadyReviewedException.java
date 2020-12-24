package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import lombok.Getter;

import java.util.Date;

@Getter
public class RegistrationAlreadyReviewedException extends RuntimeException {
    private final RegistrationId registrationId;

    private final Date reviewedAt;

    private final String reviewedBy;

    public RegistrationAlreadyReviewedException(Registration registration) {
        this.registrationId = registration.getRegistrationId();
        this.reviewedAt = registration.getReviewDetails().getReviewedAt();
        this.reviewedBy = registration.getReviewDetails().getReviewedBy();
    }
}
