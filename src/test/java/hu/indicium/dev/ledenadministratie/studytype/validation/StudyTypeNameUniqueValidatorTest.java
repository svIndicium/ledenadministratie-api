package hu.indicium.dev.ledenadministratie.studytype.validation;

import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Study type unique name validation")
class StudyTypeNameUniqueValidatorTest {

    @MockBean
    private StudyTypeService studyTypeService;

    @Autowired
    private StudyTypeNameUniqueValidator studyTypeNameUniqueValidator;

    @Test
    @DisplayName("Pass when no study type exists with the same name")
    void shouldPass_whenNoStudyTypeWithTheSameNameExists() {
        StudyType studyType = new StudyType("SD");

        when(studyTypeService.isNameInUse(eq("SD"))).thenReturn(false);

        studyTypeNameUniqueValidator.validate(studyType);
    }

    @Test
    @DisplayName("Throw exception when study type exists with the same name")
    void shouldThrowException_whenAStudyTypeWithTheSameNameExists() {
        StudyType studyType = new StudyType("SD");

        when(studyTypeService.isNameInUse(eq("SD"))).thenReturn(true);
        try {
            studyTypeNameUniqueValidator.validate(studyType);
        } catch (Exception e) {
            assertThat(e.getMessage()).isEqualTo(StudyTypeNameUniqueValidator.ERROR_MESSAGE);
        }
    }

    @TestConfiguration
    static class StudyTypeNameUniqueValidatorTestContextConfiguration {
        @Autowired
        private StudyTypeService studyTypeService;

        @Bean
        public StudyTypeNameUniqueValidator studyTypeNameUniqueValidator() {
            return new StudyTypeNameUniqueValidator(studyTypeService);
        }
    }

}