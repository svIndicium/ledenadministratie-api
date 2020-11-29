package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewDetails;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class ReviewDetailsDto {
    private Date reviewedAt;

    private String reviewedBy;

    private String comment;

    public ReviewDetailsDto(ReviewDetails reviewDetails) {
        this.reviewedAt = reviewDetails.getReviewedAt();
        this.reviewedBy = reviewDetails.getReviewedBy();
        this.comment = reviewDetails.getComment();
    }
}
