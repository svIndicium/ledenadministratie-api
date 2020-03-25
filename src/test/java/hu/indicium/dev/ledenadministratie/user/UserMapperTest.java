//package hu.indicium.dev.ledenadministratie.user;
//
//import hu.indicium.dev.ledenadministratie.studytype.StudyType;
//import hu.indicium.dev.ledenadministratie.studytype.StudyTypeMapper;
//import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
//import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
//import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@DisplayName("User Mapper")
//class UserMapperTest {
//
//    @MockBean
//    private StudyTypeService studyTypeService;
//
//    @MockBean
//    private StudyTypeMapper studyTypeMapper;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    @DisplayName("Convert entity to DTO")
//    void shouldReturnCorrectDTO_whenConvertEntityToDTO() {
//        StudyType studyType = new StudyType("Software Development");
//        studyType.setId(1L);
//
//        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
//        studyTypeDTO.setName(studyType.getName());
//        studyTypeDTO.setId(studyType.getId());
//
//        User user = new User();
//        user.setId(1L);
//        user.setFirstName("John");
//        user.setMiddleName("Nicholas");
//        user.setLastName("Doe");
//        user.setEmail("John@doe.com");
//        user.setPhoneNumber("+31612345678");
//        user.setToReceiveNewsletter(true);
//        user.setDateOfBirth(new Date());
//        user.setStudyType(studyType);
//
//        when(studyTypeMapper.toDTO(any(StudyType.class))).thenReturn(studyTypeDTO);
//
//        UserDTO userDTO = userMapper.toDTO(user);
//
//        assertThat(userDTO).isNotNull();
//        assertThat(userDTO.getId()).isEqualTo(user.getId());
//        assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstName());
//        assertThat(userDTO.getMiddleName()).isEqualTo(user.getMiddleName());
//        assertThat(userDTO.getLastName()).isEqualTo(user.getLastName());
//        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
//        assertThat(userDTO.getPhoneNumber()).isEqualTo(user.getPhoneNumber());
//        assertThat(userDTO.isToReceiveNewsletter()).isEqualTo(user.isToReceiveNewsletter());
//        assertThat(userDTO.getDateOfBirth()).isEqualTo(user.getDateOfBirth());
//        assertThat(userDTO.getStudyType()).isNotNull();
//        assertThat(userDTO.getStudyType()).isEqualToComparingFieldByField(studyTypeDTO);
//    }
//
//    @Test
//    @DisplayName("Convert DTO to entity")
//    void shouldReturnCorrectEntity_whenConvertDTOToEntity() {
//        StudyType studyType = new StudyType("Software Development");
//        studyType.setId(1L);
//
//        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
//        studyTypeDTO.setName(studyType.getName());
//        studyTypeDTO.setId(studyType.getId());
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setId(1L);
//        userDTO.setFirstName("John");
//        userDTO.setMiddleName("Nicholas");
//        userDTO.setLastName("Doe");
//        userDTO.setEmail("John@doe.com");
//        userDTO.setDateOfBirth(new Date());
//        userDTO.setPhoneNumber("+31612345678");
//        userDTO.setToReceiveNewsletter(true);
//        userDTO.setStudyType(studyTypeDTO);
//
//        when(studyTypeService.getStudyTypeById(any(Long.class))).thenReturn(studyTypeDTO);
//        when(studyTypeMapper.toEntity(any(StudyTypeDTO.class))).thenReturn(studyType);
//
//        User user = userMapper.toEntity(userDTO);
//
//        assertThat(user).isNotNull();
//        assertThat(user.getId()).isEqualTo(userDTO.getId());
//        assertThat(user.getFirstName()).isEqualTo(userDTO.getFirstName());
//        assertThat(user.getMiddleName()).isEqualTo(userDTO.getMiddleName());
//        assertThat(user.getLastName()).isEqualTo(userDTO.getLastName());
//        assertThat(user.getEmail()).isEqualTo(userDTO.getEmail());
//        assertThat(user.getPhoneNumber()).isEqualTo(userDTO.getPhoneNumber());
//        assertThat(user.isToReceiveNewsletter()).isEqualTo(userDTO.isToReceiveNewsletter());
//        assertThat(user.getDateOfBirth()).isEqualTo(userDTO.getDateOfBirth());
//        assertThat(user.getStudyType()).isNotNull();
//        assertThat(user.getStudyType()).isEqualToComparingFieldByField(studyType);
//    }
//
//    @TestConfiguration
//    static class UserMapperTestContextConfiguration {
//        @Autowired
//        private StudyTypeService studyTypeService;
//
//        @Autowired
//        private StudyTypeMapper studyTypeMapper;
//
//        @Bean
//        public UserMapper userMapper() {
//            return new UserMapper(studyTypeService, studyTypeMapper);
//        }
//    }
//
//}