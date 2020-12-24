package hu.indicium.dev.ledenadministratie.domain.model.studytype;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Study type Id")
class StudyTypeIdTest {
    @Test
    @DisplayName("Create study type id")
    void createNewStudyTypeId() {
        UUID id = UUID.randomUUID();
        StudyTypeId studyTypeId = StudyTypeId.fromId(id);

        assertThat(studyTypeId).isNotNull();
        assertThat(studyTypeId.getId()).isEqualTo(id);
    }
}