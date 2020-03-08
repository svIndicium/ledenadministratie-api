package hu.indicium.dev.ledenadministratie.studytype;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StudyTypeServiceImpl.class, ModelMapper.class})
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

    @Test
    @DisplayName("Study type with the same name exists")
    void shouldReturnTrue_whenRepositorySaysItsTrue() {
        when(studyTypeRepository.existsByName(eq("SD"))).thenReturn(true);

        assertThat(studyTypeService.isNameInUse("SD")).isTrue();
    }

    @Test
    @DisplayName("Study type with the same name does not exist")
    void shouldReturnFalse_whenRepositorySaysItsFalse() {
        when(studyTypeRepository.existsByName(eq("SD"))).thenReturn(false);

        assertThat(studyTypeService.isNameInUse("SD")).isFalse();
    }

    @Test
    @DisplayName("Get all study types")
    void shouldReturnAListOfDTOs_whenGetAllStudyTypes() {
        StudyType studyType = new StudyType("SD");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        StudyType studyType1 = new StudyType("TI");
        studyType1.setId(2L);

        StudyTypeDTO studyTypeDTO1 = new StudyTypeDTO();
        studyTypeDTO1.setId(studyType.getId());
        studyTypeDTO1.setName(studyType.getName());

        when(studyTypeMapper.toDTO(eq(studyType))).thenReturn(studyTypeDTO);
        when(studyTypeMapper.toDTO(eq(studyType1))).thenReturn(studyTypeDTO1);

        when(studyTypeRepository.findAll()).thenReturn(Arrays.asList(studyType, studyType1));

        List<StudyTypeDTO> studyTypeDTOS = studyTypeService.getAllStudyTypes();

        assertThat(studyTypeDTOS).hasSize(2);
        assertThat(studyTypeDTOS.get(0)).isEqualToComparingFieldByField(studyTypeDTO);
        assertThat(studyTypeDTOS.get(1)).isEqualToComparingFieldByField(studyTypeDTO1);
    }

    @Test
    @DisplayName("Create new study type")
    void createStudyType() {
        StudyType studyType = new StudyType("SD");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        when(studyTypeMapper.toEntity(any(StudyTypeDTO.class))).thenReturn(studyType);
        when(studyTypeMapper.toDTO(any(StudyType.class))).thenReturn(studyTypeDTO);
        when(studyTypeRepository.save(any(StudyType.class))).thenReturn(studyType);

        StudyTypeDTO result = studyTypeService.createStudyType(studyTypeDTO);

        verify(studyTypeValidator, times(1)).validate(any(StudyType.class));
        verify(studyTypeRepository, times(1)).save(any(StudyType.class));

        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Validation error should stop creating user")
    void shouldNotCreateUser_whenValidationIsThrowingAnException() {
        StudyType studyType = new StudyType("SD");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        when(studyTypeMapper.toEntity(any(StudyTypeDTO.class))).thenReturn(studyType);
        when(studyTypeRepository.save(any(StudyType.class))).thenReturn(studyType);
        doThrow(new IllegalArgumentException("Exception")).when(studyTypeValidator).validate(any(StudyType.class));

        try {
            StudyTypeDTO result = studyTypeService.createStudyType(studyTypeDTO);
            fail();
        } catch (Exception e) {
            verify(studyTypeRepository, never()).save(any(StudyType.class));
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