package hu.indicium.dev.ledenadministratie.domain.model.user;

import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
        Date dateOfBirth = calendar.getTime();

        Date startDate = new Date();
        MemberDetails memberDetails = new MemberDetails(name, phoneNumber, dateOfBirth, studyType);

        assertThat(memberDetails).isNotNull();
        assertThat(memberDetails.getName()).isEqualTo(name);
        assertThat(memberDetails.getStudyType()).isEqualTo(studyType);
        assertThat(memberDetails.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(memberDetails.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(memberDetails.getCreatedAt()).isCloseTo(startDate, 100);
        assertThat(memberDetails.getCreatedAt()).isBeforeOrEqualsTo(new Date());
    }
}