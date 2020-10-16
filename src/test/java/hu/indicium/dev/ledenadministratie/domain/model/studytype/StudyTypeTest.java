package hu.indicium.dev.ledenadministratie.domain.model.studytype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Study type")
class StudyTypeTest {

    @Test
    @DisplayName("Create study type")
    void createStudyType() {
        StudyTypeId studyTypeId = StudyTypeId.fromId(UUID.randomUUID());
        String shortName = "SD";
        String name = "Software development";

        StudyType studyType = new StudyType(studyTypeId, shortName, name);

        assertThat(studyType).isNotNull();
        assertThat(studyType.getStudyTypeId()).isEqualTo(studyTypeId);
        assertThat(studyType.getShortName()).isEqualTo(shortName);
        assertThat(studyType.getName()).isEqualTo(name);
    }
}