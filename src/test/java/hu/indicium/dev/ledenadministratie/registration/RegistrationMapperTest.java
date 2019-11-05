package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeMapper;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DisplayName("Registration Mapper")
class RegistrationMapperTest {

    @MockBean
    private StudyTypeService studyTypeService;

    @MockBean
    private StudyTypeMapper studyTypeMapper;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Test
    @DisplayName("Convert entity to DTO")
    void shouldReturnCorrectDTO_whenConvertEntityToDTO() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setName(studyType.getName());
        studyTypeDTO.setId(studyType.getId());

        Registration registration = new Registration();
        registration.setId(1L);
        registration.setFirstName("John");
        registration.setMiddleName("Nicholas");
        registration.setLastName("Doe");
        registration.setEmail("John@doe.com");
        registration.setPhoneNumber("+31612345678");
        registration.setToReceiveNewsletter(true);
        registration.setDateOfBirth(new Date());
        registration.setStudyType(studyType);
        registration.setApproved(false);

        when(studyTypeMapper.toDTO(any(StudyType.class))).thenReturn(studyTypeDTO);

        RegistrationDTO registrationDTO = registrationMapper.toDTO(registration);

        assertThat(registrationDTO).isNotNull();
        assertThat(registrationDTO.getId()).isEqualTo(registration.getId());
        assertThat(registrationDTO.getFirstName()).isEqualTo(registration.getFirstName());
        assertThat(registrationDTO.getMiddleName()).isEqualTo(registration.getMiddleName());
        assertThat(registrationDTO.getLastName()).isEqualTo(registration.getLastName());
        assertThat(registrationDTO.getEmail()).isEqualTo(registration.getEmail());
        assertThat(registrationDTO.getPhoneNumber()).isEqualTo(registration.getPhoneNumber());
        assertThat(registrationDTO.isToReceiveNewsletter()).isEqualTo(registration.isToReceiveNewsletter());
        assertThat(registrationDTO.getDateOfBirth()).isEqualTo(registration.getDateOfBirth());
        assertThat(registrationDTO.getStudyType()).isNotNull();
        assertThat(registrationDTO.getStudyType()).isEqualToComparingFieldByField(studyTypeDTO);
        assertThat(registrationDTO.isApproved()).isEqualTo(registration.isApproved());
        assertThat(registrationDTO.getComment()).isNull();
        assertThat(registrationDTO.getFinalizedAt()).isNull();
        assertThat(registrationDTO.getFinalizedBy()).isNull();
    }

    @Test
    @DisplayName("Convert entity to DTO")
    void shouldReturnCorrectDTO_whenConvertEntityToDTO_afterFinalization() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setName(studyType.getName());
        studyTypeDTO.setId(studyType.getId());

        Registration registration = new Registration();
        registration.setId(1L);
        registration.setFirstName("John");
        registration.setMiddleName("Nicholas");
        registration.setLastName("Doe");
        registration.setEmail("John@doe.com");
        registration.setPhoneNumber("+31612345678");
        registration.setToReceiveNewsletter(true);
        registration.setDateOfBirth(new Date());
        registration.setStudyType(studyType);
        registration.setApproved(true);
        registration.setFinalizedBy("Rick Astley");
        registration.setFinalizedAt(new Date());
        registration.setComment("Never give us up!");

        when(studyTypeMapper.toDTO(any(StudyType.class))).thenReturn(studyTypeDTO);

        RegistrationDTO registrationDTO = registrationMapper.toDTO(registration);

        assertThat(registrationDTO).isNotNull();
        assertThat(registrationDTO.getId()).isEqualTo(registration.getId());
        assertThat(registrationDTO.getFirstName()).isEqualTo(registration.getFirstName());
        assertThat(registrationDTO.getMiddleName()).isEqualTo(registration.getMiddleName());
        assertThat(registrationDTO.getLastName()).isEqualTo(registration.getLastName());
        assertThat(registrationDTO.getEmail()).isEqualTo(registration.getEmail());
        assertThat(registrationDTO.getPhoneNumber()).isEqualTo(registration.getPhoneNumber());
        assertThat(registrationDTO.isToReceiveNewsletter()).isEqualTo(registration.isToReceiveNewsletter());
        assertThat(registrationDTO.getDateOfBirth()).isEqualTo(registration.getDateOfBirth());
        assertThat(registrationDTO.getStudyType()).isNotNull();
        assertThat(registrationDTO.getStudyType()).isEqualToComparingFieldByField(studyTypeDTO);
        assertThat(registrationDTO.isApproved()).isEqualTo(registration.isApproved());
        assertThat(registrationDTO.getComment()).isEqualTo(registration.getComment());
        assertThat(registrationDTO.getFinalizedAt()).isEqualTo(registration.getFinalizedAt());
        assertThat(registrationDTO.getFinalizedBy()).isEqualTo(registration.getFinalizedBy());
    }

    @Test
    @DisplayName("Convert DTO to entity")
    void shouldReturnCorrectEntity_whenConvertDTOToEntity() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setName(studyType.getName());
        studyTypeDTO.setId(studyType.getId());

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setId(1L);
        registrationDTO.setFirstName("John");
        registrationDTO.setMiddleName("Nicholas");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("John@doe.com");
        registrationDTO.setDateOfBirth(new Date());
        registrationDTO.setPhoneNumber("+31612345678");
        registrationDTO.setToReceiveNewsletter(true);
        registrationDTO.setStudyType(studyTypeDTO);
        registrationDTO.setApproved(true);
        registrationDTO.setFinalizedBy("Rick Astley");
        registrationDTO.setFinalizedAt(new Date());
        registrationDTO.setComment("Never let us down!");

        when(studyTypeService.getStudyTypeById(any(Long.class))).thenReturn(studyTypeDTO);
        when(studyTypeMapper.toEntity(any(StudyTypeDTO.class))).thenReturn(studyType);

        Registration registration = registrationMapper.toEntity(registrationDTO);

        assertThat(registration).isNotNull();
        assertThat(registration.getId()).isEqualTo(registrationDTO.getId());
        assertThat(registration.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(registration.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(registration.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(registration.getEmail()).isEqualTo(registrationDTO.getEmail());
        assertThat(registration.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(registration.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(registration.getDateOfBirth()).isEqualTo(registrationDTO.getDateOfBirth());
        assertThat(registration.getStudyType()).isNotNull();
        assertThat(registration.getStudyType()).isEqualToComparingFieldByField(studyType);
        assertThat(registration.isApproved()).isEqualTo(registrationDTO.isApproved());
        assertThat(registration.getComment()).isEqualTo(registrationDTO.getComment());
        assertThat(registration.getFinalizedAt()).isEqualTo(registrationDTO.getFinalizedAt());
        assertThat(registration.getFinalizedBy()).isEqualTo(registrationDTO.getFinalizedBy());
    }

    @TestConfiguration
    static class RegistrationMapperTestContextConfiguration {
        @Autowired
        private StudyTypeService studyTypeService;

        @Autowired
        private StudyTypeMapper studyTypeMapper;

        @Bean
        public RegistrationMapper registrationMapperMapper() {
            return new RegistrationMapper(studyTypeService, studyTypeMapper);
        }
    }

}