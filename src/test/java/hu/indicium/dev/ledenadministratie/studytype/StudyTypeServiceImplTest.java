package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Study type service")
class StudyTypeServiceImplTest {

    @MockBean
    private StudyTypeRepository studyTypeRepository;

    @MockBean
    private StudyTypeMapper studyTypeMapper;

    @MockBean
    private Validator<StudyType> studyTypeValidator;

    @Autowired
    private StudyTypeService studyTypeService;

    @Test
    @DisplayName("Get study type by id")
    void shouldReturnStudyType_whenGetStudyTypeById() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        when(studyTypeMapper.toDTO(any(StudyType.class))).thenReturn(studyTypeDTO);
        when(studyTypeRepository.findById(eq(1L))).thenReturn(Optional.of(studyType));

        StudyTypeDTO foundStudyType = studyTypeService.getStudyTypeById(1L);

        assertThat(foundStudyType.getId()).isEqualTo(studyType.getId());
        assertThat(foundStudyType.getName()).isEqualTo(studyType.getName());
    }

    @Test
    @DisplayName("Get non-existing study type by id")
    void shouldThrowEntityNotFoundException_whenGetInvalidStudyTypeId() {
        when(studyTypeRepository.findById(eq(1L))).thenReturn(Optional.empty());

        try {
            studyTypeService.getStudyTypeById(1L);
            fail();
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Studytype 1 not found");
        }
    }

    @TestConfiguration
    static class StudyTypeServiceTestContextConfiguration {

        @Autowired
        private StudyTypeRepository studyTypeRepository;

        @Autowired
        private StudyTypeMapper studyTypeMapper;

        @Autowired
        private Validator<StudyType> studyTypeValidator;

        @Bean
        public StudyTypeService studyTypeService() {
            return new StudyTypeServiceImpl(studyTypeRepository, studyTypeMapper, studyTypeValidator);
        }
    }
}