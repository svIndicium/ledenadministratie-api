package hu.indicium.dev.ledenadministratie.domain.model.user;

import hu.indicium.dev.ledenadministratie.domain.AssertionConcern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

@Embeddable
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ReviewDetails extends AssertionConcern {
    @Column(name = "reviewed_at")
    private Date reviewedAt;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    @Column(name = "comment")
    private String comment;

    private ReviewDetails(String reviewedBy) {
        this.reviewedAt = new Date();
        this.setReviewedBy(reviewedBy);
    }

    private ReviewDetails(String reviewedBy, String comment) {
        this(reviewedBy);
        this.setComment(comment);
    }

    public static ReviewDetails approve(String reviewedBy) {
        return new ReviewDetails(reviewedBy);
    }

    public static ReviewDetails deny(String reviewedBy, String comment) {
        return new ReviewDetails(reviewedBy, comment);
    }

    public void setReviewedBy(String reviewedBy) {
        assertArgumentNotNull(reviewedBy, "Name of reviewer must be given.");
        assertArgumentLength(reviewedBy, 255, "Name of reviewer must be shorter than 255 characters.");

        this.reviewedBy = reviewedBy;
    }

    public void setComment(String comment) {
        assertArgumentNotNull(comment, "Comment must be given.");
        assertArgumentLength(comment, 255, "Comment must be shorter than 255 characters.");

        this.comment = comment;
    }
}
