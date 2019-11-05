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

        UserDTO userDTO = registrationUserMapper.toDTO(registrationDTO);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isNull();
        assertThat(userDTO.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(userDTO.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(userDTO.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(userDTO.getEmail()).isEqualTo(registrationDTO.getEmail());
        assertThat(userDTO.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(userDTO.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(userDTO.getDateOfBirth()).isEqualTo(registrationDTO.getDateOfBirth());
        assertThat(userDTO.getStudyType()).isNotNull();
        assertThat(userDTO.getStudyType()).isEqualToComparingFieldByField(studyType);
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