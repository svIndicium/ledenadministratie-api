package hu.indicium.dev.ledenadministratie.domain.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Review details")
class ReviewDetailsTest {

    @Test
    @DisplayName("Create approved review details")
    void createApprovedReviewDetails() {
        String reviewedBy = "Miguel Gomez";

        Date startDate = new Date();
        ReviewDetails reviewDetails = ReviewDetails.approve(reviewedBy);

        assertThat(reviewDetails).isNotNull();
        assertThat(reviewDetails.getReviewedBy()).isEqualTo(reviewedBy);
        assertThat(reviewDetails.getComment()).isNull();
        assertThat(reviewDetails.getReviewedAt()).isCloseTo(startDate, 100);
        assertThat(reviewDetails.getReviewedAt()).isBeforeOrEqualsTo(new Date());
    }

    @Test
    @DisplayName("Create denied review details")
    void createDeniedReviewDetails() {
        String reviewedBy = "Miguel Gomez";
        String comment = "Troll";

        Date startDate = new Date();
        ReviewDetails reviewDetails = ReviewDetails.deny(reviewedBy, comment);

        assertThat(reviewDetails).isNotNull();
        assertThat(reviewDetails.getReviewedBy()).isEqualTo(reviewedBy);
        assertThat(reviewDetails.getComment()).isEqualTo(comment);
        assertThat(reviewDetails.getReviewedAt()).isCloseTo(startDate, 100);
        assertThat(reviewDetails.getReviewedAt()).isBeforeOrEqualsTo(new Date());
    }
}