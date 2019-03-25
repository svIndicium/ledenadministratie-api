package hu.indicium.dev.lit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.lit.user.dto.NewUserDTO;
import hu.indicium.dev.lit.userdata.Gender;
import hu.indicium.dev.lit.userdata.UserData;
import hu.indicium.dev.lit.userdata.UserDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@DisplayName("User Controller")
@Tag("Controller")
class UserControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserServiceInterface userService;

    @Test
    @DisplayName("Create new user")
//    @WithMockOAuth2Scope(scope = "lit:create")
    void shouldCreateNewUser_whenCreateNewUser() throws Exception {

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
        user.setUserData(userData);

        when(userService.createUser(any(NewUserDTO.class))).thenReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("kek")));
    }

//    @Test
//    @DisplayName("Get all users")
//    void shouldReturnJsonArray_whenGetAllUsers() throws Exception {
//        User user = new User(1L);
//        User user1 = new User(2L);
//        List<User> allUsers = Arrays.asList(user, user1);
//
//        mvc.perform(get("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("user")))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
//                .andExpect(jsonPath("$[1].firstName", is(user1.getFirstName())));
//    }

//    @Test
//    @DisplayName("Update user")
//    void shouldReturnUpdatedUser_whenUpdateUser() throws Exception {
//        User user = new User("Alex", "Jane", "alex.jones@alex.com", "");
//        User user1 = new User("Jane", "Jones", "jane.jones@jones.com", "");
//        user.setId(1L);
//
//        when(service.updateUser(any(User.class))).thenReturn(user1);
//
//        mvc.perform(put("/api/users/1")
//                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .content(objectMapper.writer().writeValueAsString(user1))
//                .with(user("user"))
//                .with(csrf()))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writer().writeValueAsString(modelMapper.map(user1, ExtendedUserDTO.class))));
//    }

//    @Test
//    @DisplayName("Get single user")
//    void shouldReturnUser_whenGetSingleUser() throws Exception {
//        User user = new User("Alex", "Jones", "alex.jones@alex.com", "");
//        user.setId(1L);
//
//        when(service.getUserById(1L)).thenReturn(user);
//
//        mvc.perform(get("/api/users/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("user")))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
//                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
//                .andExpect(jsonPath("$.email", is(user.getEmail())));
//    }
//
//    @Test
//    @DisplayName("Delete user")
//    void shouldDoNothing_whenDeleteUser() throws Exception {
//        mvc.perform(delete("/api/users/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("user"))
//                .with(csrf()))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("Get all users by company id")
//    void shouldReturnJsonArray_whenGetAllUsersByCompanyId() throws Exception {
//        User user = new User("Alex", "Jones", "alex.jones@alex.com", "");
//        User user1 = new User("Jane", "Jones", "jane.jones@jones.com", "");
//        List<User> allUsers = Arrays.asList(user, user1);
//
//        Company company = new Company();
//        company.setId(1L);
//
//        given(companyService.getCompanyById(1L)).willReturn(company);
//        given(service.getAllUsersByCompany(company)).willReturn(allUsers);
//
//        mvc.perform(get("/api/companies/1/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("user")))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
//                .andExpect(jsonPath("$[1].firstName", is(user1.getFirstName())));
//    }
//
//    @Test
//    @DisplayName("Get all users by invalid company id throws exception")
//    void shouldThrowCompanyNotFoundException() throws Exception {
//
//        when(companyService.getCompanyById(4L)).thenThrow(new CompanyNotFoundException(4L));
//
//        mvc.perform(get("/api/companies/4/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("user")))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", is("Could not find company 4")));
//    }
//
//    @Test
//    @DisplayName("Get user by unknown user id")
//    void shouldThrowUserNotFoundException_whenGetInvalidUserId() throws Exception {
//
//        when(service.getUserById(4L)).thenThrow(new UserNotFoundException());
//
//        mvc.perform(get("/users/4")
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(user("user")))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.message", is("Could not find user 4")));
//    }
}