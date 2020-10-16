package hu.indicium.dev.ledenadministratie.domain.model.user;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member details")
class MemberDetailsTest {

    @Test
    @DisplayName("Create member details")
    void createMemberDetails() {
        Name name = new Name("Miguel", "Don", "Gomez");
        StudyType studyType = new StudyType(StudyTypeId.fromId(UUID.randomUUID()), "SD", "Software Development");
        String phoneNumber = "+31612345678";
        Date dateOfBirth = new Date();

        Date startDate = new Date();
        MemberDetails memberDetails = new MemberDetails(name, phoneNumber, dateOfBirth, studyType);

        assertThat(memberDetails).isNotNull();
        assertThat(memberDetails.getName()).isEqualTo(name);
        assertThat(memberDetails.getStudyType()).isEqualTo(studyType);
        assertThat(memberDetails.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(memberDetails.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(memberDetails.getCreatedAt()).isAfterOrEqualsTo(startDate);
        assertThat(memberDetails.getCreatedAt()).isBefore(new Date());
    }
}