package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.hooks.CreationHook;
import hu.indicium.dev.ledenadministratie.hooks.UpdateHook;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
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
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserServiceImpl.class)
@DisplayName("User Service")
@Tag("Services")
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Mapper<User, UserDTO> userMapper;

    @MockBean
    private Validator<User> userValidator;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private CreationHook<UserDTO> creationHook;

    @MockBean
    private UpdateHook<UserDTO> updateHook;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Create user")
    void createUser() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        User user = new User();
        user.setFirstName("John");
        user.setMiddleName("Daniel");
        user.setLastName("Doe");
        user.setEmail("John@doe.com");
        user.setStudyType(studyType);
        user.setToReceiveNewsletter(false);
        user.setDateOfBirth(new Date());

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setStudyType(studyTypeDTO);
        userDTO.setToReceiveNewsletter(user.isToReceiveNewsletter());
        userDTO.setDateOfBirth(user.getDateOfBirth());

        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO returnedUserDTO = userService.createUser(userDTO);

        verify(userRepository).save(eq(user));
        verify(userValidator, atLeastOnce()).validate(any(User.class));
        verify(creationHook, times(1)).execute(null, userDTO);
        verify(updateHook, never()).execute(any(UserDTO.class), any(UserDTO.class));

        assertThat(returnedUserDTO).isNotNull();
    }

    @Test
    @DisplayName("Create invalid user should not be persisted")
    void creatingUserFails_shouldNotBePersisted() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        User user = new User();
        user.setFirstName("John");
        user.setMiddleName("Daniel");
        user.setLastName("Doe");
        user.setEmail("John@doe.com");
        user.setStudyType(studyType);
        user.setToReceiveNewsletter(true);
        user.setDateOfBirth(new Date());

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setStudyType(studyTypeDTO);
        userDTO.setToReceiveNewsletter(user.isToReceiveNewsletter());
        userDTO.setDateOfBirth(user.getDateOfBirth());

        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        doThrow(new IllegalArgumentException("User not legitimate")).when(userValidator).validate(any(User.class));

        UserDTO returnedUserDTO = null;

        try {
            returnedUserDTO = userService.createUser(userDTO);
            fail();
        } catch (Exception e) {
            assertThat(true).isTrue();
        }


        verify(userValidator, atLeastOnce()).validate(any(User.class));
        verify(userRepository, never()).save(eq(user));
        verify(creationHook, never()).execute(any(UserDTO.class), any(UserDTO.class));
        verify(updateHook, never()).execute(any(UserDTO.class), any(UserDTO.class));

        assertThat(returnedUserDTO).isNull();
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setMiddleName("Daniel");
        user.setLastName("Doe");
        user.setEmail("John@doe.com");
        user.setStudyType(studyType);
        user.setToReceiveNewsletter(true);
        user.setDateOfBirth(new Date());

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setStudyType(studyTypeDTO);
        userDTO.setToReceiveNewsletter(user.isToReceiveNewsletter());
        userDTO.setDateOfBirth(user.getDateOfBirth());

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        UserDTO receivedUser = userService.getUserById(1L);

        assertThat(receivedUser).isEqualTo(userDTO);
    }

    @Test
    @DisplayName("Throw exception when getting non-existing user")
    void shouldThrowException_whenGetNonExistingUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        try {
            UserDTO receivedUser = userService.getUserById(1L);
            fail();
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
            assertThat(ex.getMessage()).isEqualTo("User 1 not found!");
        }


    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private Mapper<User, UserDTO> userMapper;

        @Autowired
        private Validator<User> userValidator;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private CreationHook<UserDTO> creationHook;

        @Autowired
        private UpdateHook<UserDTO> updateHook;

        @Bean
        public UserService userService() {
            return new UserServiceImpl(userRepository, userValidator, userMapper, modelMapper, creationHook, updateHook);
        }
    }
}