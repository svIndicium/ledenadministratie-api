package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Registration User Mapper")
class RegistrationUserMapperTest {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RegistrationUserMapper registrationUserMapper;

    @Test
    @DisplayName("Convert Registration to UserDTO")
    void shouldReturnCorrectUserDTO_whenConvertRegistration() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        RegistrationDTO registrationDTO = getRegistrationDTO();

        UserDTO userDTO = registrationUserMapper.toDTO(registrationDTO);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isNull();
        assertThat(userDTO.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(userDTO.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(userDTO.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(userDTO.getEmail()).isEqualTo(registrationDTO.getMailAddress());
        assertThat(userDTO.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(userDTO.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(userDTO.getDateOfBirth()).isEqualTo(registrationDTO.getDateOfBirth());
        assertThat(userDTO.getStudyType()).isNotNull();
        assertThat(userDTO.getStudyType()).isEqualToComparingFieldByField(studyType);
    }

    @Test
    @DisplayName("Convert UserDTO to Registration")
    void shouldReturnCorrectRegistration_whenConvertUserDTO() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        UserDTO userDTO = getUserDTO();

        RegistrationDTO registrationDTO = registrationUserMapper.toEntity(userDTO);

        assertThat(registrationDTO).isNotNull();
        assertThat(registrationDTO.getId()).isEqualTo(userDTO.getId());
        assertThat(registrationDTO.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(registrationDTO.getMiddleName()).isEqualTo(userDTO.getMiddleName());
        assertThat(registrationDTO.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(registrationDTO.getMailAddress()).isEqualTo(userDTO.getEmail());
        assertThat(registrationDTO.getPhoneNumber()).isEqualTo(userDTO.getPhoneNumber());
        assertThat(registrationDTO.isToReceiveNewsletter()).isEqualTo(userDTO.isToReceiveNewsletter());
        assertThat(registrationDTO.getDateOfBirth()).isEqualTo(userDTO.getDateOfBirth());
        assertThat(registrationDTO.getStudyType()).isNotNull();
        assertThat(registrationDTO.getStudyType()).isEqualToComparingFieldByField(studyType);
    }

    private UserDTO getUserDTO() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setName(studyType.getName());
        studyTypeDTO.setId(studyType.getId());

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setMiddleName("Nicholas");
        userDTO.setLastName("Doe");
        userDTO.setEmail("John@doe.com");
        userDTO.setDateOfBirth(new Date());
        userDTO.setPhoneNumber("+31612345678");
        userDTO.setToReceiveNewsletter(true);
        userDTO.setStudyType(studyTypeDTO);
        return userDTO;
    }

    private RegistrationDTO getRegistrationDTO() {
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
        registrationDTO.setMailAddress("John@doe.com");
        registrationDTO.setDateOfBirth(new Date());
        registrationDTO.setPhoneNumber("+31612345678");
        registrationDTO.setToReceiveNewsletter(true);
        registrationDTO.setStudyType(studyTypeDTO);
        registrationDTO.setApproved(true);
        registrationDTO.setFinalizedBy("Rick Astley");
        registrationDTO.setFinalizedAt(new Date());
        registrationDTO.setComment("Never let us down!");
        return registrationDTO;
    }

    @TestConfiguration
    static class RegistrationUserMapperTestContextConfiguration {
        @Autowired
        private ModelMapper modelMapper;

        @Bean
        public RegistrationUserMapper registrationUserMapper() {
            return new RegistrationUserMapper(modelMapper);
        }
    }
}