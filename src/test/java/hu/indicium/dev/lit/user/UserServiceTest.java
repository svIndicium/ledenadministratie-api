package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.user.dto.NewUserDTO;
import hu.indicium.dev.lit.user.exceptions.UserNotFoundException;
import hu.indicium.dev.lit.userdata.Gender;
import hu.indicium.dev.lit.userdata.UserData;
import hu.indicium.dev.lit.userdata.UserDataBuilder;
import hu.indicium.dev.lit.userdata.UserDataServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
@DisplayName("User Service")
@Tag("services")
class UserServiceTest {

    @Autowired
    private UserServiceInterface userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserDataServiceInterface userDataService;

    @Test
    @DisplayName("Create user")
    void shouldCreateUserData_whenCreateNewUser() {
        NewUserDTO userDTO = new NewUserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("Alex");
        userDTO.setLastName("Jones");
        userDTO.setStreet("Heidelberglaan");
        userDTO.setHouseNumber("15");
        userDTO.setCity("Utrecht");
        userDTO.setZipCode("3448CS");
        userDTO.setCountry("Netherlands");
        userDTO.setEmail("alex.jones@jones.com");
        userDTO.setPhoneNumber("123123");
        userDTO.setDateOfBirth(new Date());
        userDTO.setGender(Gender.MALE);
        userDTO.setStudentId(123123123);

        User user = new User(1L);

        UserData userData = new UserDataBuilder(userDTO.getId())
                .setUser(user)
                .setFirstName(userDTO.getFirstName())
                .setEmail(userDTO.getEmail())
                .setLastName(userDTO.getLastName())
                .setGender(userDTO.getGender())
                .setDateOfBirth(userDTO.getDateOfBirth())
                .setStreet(userDTO.getStreet())
                .setHouseNumber(userDTO.getHouseNumber())
                .setZipCode(userDTO.getZipCode())
                .setCity(userDTO.getCity())
                .setCountry(userDTO.getCountry())
                .setPhoneNumber(userDTO.getPhoneNumber())
                .setStudentId(userDTO.getStudentId())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDataService.saveUserData(any(User.class), any(NewUserDTO.class))).thenReturn(userData);

        assertThat(userService.createUser(userDTO))
                .hasFieldOrPropertyWithValue("id", user.getId())
                .hasFieldOrPropertyWithValue("userData", userData);
    }

    @Test
    @DisplayName("Get user by id")
    void shouldReturnUser_whenGetUserById() {
        User user = new User(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThat(userService.getUserById(1L)).isEqualTo(user);
    }

    @Test
    @DisplayName("Throw exception when get non-existing user")
    void shouldThrowUserNotFoundException_whenGetUnknownId() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            userService.getUserById(1L);
            fail("Could get non-existant user");
        } catch (Exception ex) {
            assertThat(ex)
                    .isInstanceOf(UserNotFoundException.class);
        }
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private UserDataServiceInterface userDataService;

        @Bean
        public UserServiceInterface userServiceInterface() {
            return new UserService(userRepository, userDataService);
        }
    }
}
