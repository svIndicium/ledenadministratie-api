package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User Mapper")
class UserMapperTest {

    @Test
    @DisplayName("Convert entity to DTO")
    void shouldReturnCorrectDTO_whenConvertEntityToDTO() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setMiddleName("Nicholas");
        user.setLastName("Doe");
        user.setPhoneNumber("+31612345678");
        user.setDateOfBirth(new Date());
        user.setStudyType(studyType);

        UserDTO userDTO = UserMapper.map(user);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isEqualTo(user.getId());
        assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(userDTO.getMiddleName()).isEqualTo(user.getMiddleName());
        assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
        assertThat(userDTO.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
        assertThat(userDTO.getDateOfBirth()).isEqualTo(user.getDateOfBirth());
        assertThat(userDTO.getStudyTypeId()).isEqualTo(user.getStudyType().getId());
    }

    @Test
    @DisplayName("Convert DTO to entity")
    void shouldReturnCorrectEntity_whenConvertDTOToEntity() {
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
        userDTO.setDateOfBirth(new Date());
        userDTO.setPhoneNumber("+31612345678");
        userDTO.setStudyTypeId(studyType.getId());

        User user = UserMapper.map(userDTO);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(userDTO.getId());
        assertThat(user.getFirstName()).isEqualTo(userDTO.getFirstName());
        assertThat(user.getMiddleName()).isEqualTo(userDTO.getMiddleName());
        assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
        assertThat(user.getPhoneNumber()).isEqualTo(userDTO.getPhoneNumber());
        assertThat(user.getDateOfBirth()).isEqualTo(userDTO.getDateOfBirth());
        assertThat(user.getStudyType()).isNotNull();
        assertThat(user.getStudyType().getId()).isEqualTo(studyType.getId());
    }
}