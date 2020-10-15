package hu.indicium.dev.ledenadministratie.domain.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.Date;

@Embeddable
@Getter
@NoArgsConstructor
public class ReviewDetails {
    @Column(name = "reviewed_at")
    private Date reviewedAt;

    @Column(name = "reviewed_by")
    private String reviewedBy;

    @Column(name = "comment")
    private String comment;

    private ReviewDetails(String reviewedBy) {
        this.reviewedAt = new Date();
        this.reviewedBy = reviewedBy;
    }

    private ReviewDetails(String reviewedBy, String comment) {
        this(reviewedBy);
        this.comment = comment;
    }

    public static ReviewDetails approve(String reviewedBy) {
        return new ReviewDetails(reviewedBy);
    }

    public static ReviewDetails deny(String reviewedBy, String comment) {
        return new ReviewDetails(reviewedBy, comment);
    }
}
