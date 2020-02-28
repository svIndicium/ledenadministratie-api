package hu.indicium.dev.ledenadministratie.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.requests.CreateUserRequest;
import hu.indicium.dev.ledenadministratie.user.requests.UpdateUserRequest;
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

import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@DisplayName("User Controller")
@Tag("Controller")
class UserControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Create user")
    void shouldAddUser_whenCreateUser() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(userDTO.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(userDTO.getEmail())))
                .andExpect(jsonPath("$.studyType", notNullValue()))
                .andExpect(jsonPath("$.studyType.id", is(1)))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(userDTO.isToReceiveNewsletter())));
//                .andExpect(jsonPath("$.dateOfBirth", is(userDTO.getDateOfBirth())));
    }

    @Test
    @DisplayName("Create user with empty middle name")
    void shouldNotReturnMiddleNameInResult_whenCreateUserWithoutMiddleName() throws Exception {
        User user = getUser();
        UserDTO newUserDTO = getUserDTO();

        user.setMiddleName(null);
        newUserDTO.setMiddleName(null);
        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);
        userDTO.setMiddleName(null);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName").doesNotExist())
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(userDTO.getEmail())))
                .andExpect(jsonPath("$.studyType", notNullValue()))
                .andExpect(jsonPath("$.studyType.id", is(1)))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(userDTO.isToReceiveNewsletter())));
//                .andExpect(jsonPath("$.dateOfBirth", is(userDTO.getDateOfBirth())));
    }

    @Test
    @DisplayName("Create user with invalid email")
    void shouldNotSaveUser_whenCreatingUserWithInvalidEmail() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setEmail("kek");

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with null email")
    void shouldNotSaveUser_whenCreatingUserWithNullEmail() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setEmail("kek");

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with blank first name")
    void shouldNotSaveUser_whenCreatingUserWithBlankFirstName() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setFirstName("");

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with null first name")
    void shouldNotSaveUser_whenCreatingUserWithNullFirstName() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setFirstName(null);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with blank last name")
    void shouldNotSaveUser_whenCreatingUserWithBlankLastName() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setLastName("");

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with null last name")
    void shouldNotSaveUser_whenCreatingUserWithNullLastName() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setLastName(null);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with blank phone number")
    void shouldNotSaveUser_whenCreatingUserWithBlankPhoneNumber() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setPhoneNumber("");

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with null phone number")
    void shouldNotSaveUser_whenCreatingUserWithNullPhoneNumber() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setPhoneNumber(null);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with null date of birth")
    void shouldNotSaveUser_whenCreatingUserWithNullDateOfBirth() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        newUserDTO.setDateOfBirth(null);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with null study type id")
    void shouldNotSaveUser_whenCreatingUserWithNullStudyTypeId() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        createUserRequest.setStudyTypeId(null);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Create user with negative study type id")
    void shouldNotSaveUser_whenCreatingUserWithNegativeStudyTypeId() throws Exception {
        UserDTO newUserDTO = getUserDTO();

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        CreateUserRequest createUserRequest = toCreateUserRequest(newUserDTO);

        createUserRequest.setStudyTypeId(-1L);

        given(userService.createUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Update user with invalid email")
    void shouldNotUpdateUser_whenUpdatingUserWithInvalidEmail() throws Exception {
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setEmail("kek");

        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        UpdateUserRequest updateUserRequest = toUpdateUserRequest(newUserDTO);

        given(userService.updateUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(put("/user/" + userDTO.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(updateUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).updateUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Update user")
    void shouldReturnUpdatedUser_whenUpdateUser() throws Exception {
        UserDTO toBeUpdatedUserDTO = new UserDTO();
        toBeUpdatedUserDTO.setFirstName("test");

        UserDTO updatedUserDTO = getUserDTO();
        updatedUserDTO.setFirstName(toBeUpdatedUserDTO.getFirstName());
        updatedUserDTO.setId(1L);

        UpdateUserRequest updateUserRequest = toUpdateUserRequest(toBeUpdatedUserDTO);

        given(userService.updateUser(any(UserDTO.class))).willReturn(updatedUserDTO);

        mvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(updateUserRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(updatedUserDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(updatedUserDTO.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(updatedUserDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedUserDTO.getEmail())))
                .andExpect(jsonPath("$.studyType", notNullValue()))
                .andExpect(jsonPath("$.studyType.id", is(1)))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(updatedUserDTO.isToReceiveNewsletter())));

        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Get single user")
    void shouldReturnSingleUser_whenGetUserById() throws Exception {
        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);

        given(userService.getUserById(eq(1L))).willReturn(userDTO);

        mvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(userDTO.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(userDTO.getEmail())))
                .andExpect(jsonPath("$.studyType", notNullValue()))
                .andExpect(jsonPath("$.studyType.id", is(1)))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(userDTO.isToReceiveNewsletter())));
    }

    @Test
    @DisplayName("Get all users")
    void shouldReturnListOfUsers_whenGetAllUsers() throws Exception {
        UserDTO userDTO = getUserDTO();
        userDTO.setId(1L);
        UserDTO userDTO1 = getUserDTO();
        userDTO1.setId(2L);
        userDTO1.setFirstName("Daniel");
        userDTO1.setLastName("Jacob");

        given(userService.getUsers()).willReturn(Arrays.asList(userDTO, userDTO1));

        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(userDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$[0].middleName", is(userDTO.getMiddleName())))
                .andExpect(jsonPath("$[0].lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$[0].email", is(userDTO.getEmail())))
                .andExpect(jsonPath("$[0].studyType", notNullValue()))
                .andExpect(jsonPath("$[0].studyType.id", is(1)))
                .andExpect(jsonPath("$[0].toReceiveNewsletter", is(userDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$[1].id", is(userDTO1.getId().intValue())))
                .andExpect(jsonPath("$[1].firstName", is(userDTO1.getFirstName())))
                .andExpect(jsonPath("$[1].middleName", is(userDTO1.getMiddleName())))
                .andExpect(jsonPath("$[1].lastName", is(userDTO1.getLastName())))
                .andExpect(jsonPath("$[1].email", is(userDTO1.getEmail())))
                .andExpect(jsonPath("$[1].studyType", notNullValue()))
                .andExpect(jsonPath("$[1].studyType.id", is(1)))
                .andExpect(jsonPath("$[1].toReceiveNewsletter", is(userDTO1.isToReceiveNewsletter())));
    }


    private StudyType getStudyType() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);
        return studyType;
    }

    private User getUser() {
        User user = new User();
        user.setFirstName("John");
        user.setMiddleName("Daniel");
        user.setLastName("Doe");
        user.setEmail("John@doe.com");
        user.setPhoneNumber("0612345678");
        user.setStudyType(getStudyType());
        user.setToReceiveNewsletter(true);
        user.setDateOfBirth(new Date());
        return user;
    }

    private StudyTypeDTO getStudyTypeDTO() {
        StudyType studyType = getStudyType();
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());
        return studyTypeDTO;
    }

    private UserDTO getUserDTO() {
        User user = getUser();
        StudyTypeDTO studyTypeDTO = getStudyTypeDTO();
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setStudyType(studyTypeDTO);
        userDTO.setToReceiveNewsletter(user.isToReceiveNewsletter());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        return userDTO;
    }

    private CreateUserRequest toCreateUserRequest(UserDTO userDTO) {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName(userDTO.getFirstName());
        createUserRequest.setMiddleName(userDTO.getMiddleName());
        createUserRequest.setLastName(userDTO.getLastName());
        createUserRequest.setStudyTypeId(userDTO.getStudyType().getId());
        createUserRequest.setDateOfBirth(userDTO.getDateOfBirth());
        createUserRequest.setPhoneNumber(userDTO.getPhoneNumber());
        createUserRequest.setToReceiveNewsletter(userDTO.isToReceiveNewsletter());
        createUserRequest.setEmail(userDTO.getEmail());
        return createUserRequest;
    }

    private UpdateUserRequest toUpdateUserRequest(UserDTO userDTO) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName(userDTO.getFirstName());
        updateUserRequest.setMiddleName(userDTO.getMiddleName());
        updateUserRequest.setLastName(userDTO.getLastName());
        if (userDTO.getStudyType() != null) {
            updateUserRequest.setStudyTypeId(userDTO.getStudyType().getId());
        }
        updateUserRequest.setDateOfBirth(userDTO.getDateOfBirth());
        updateUserRequest.setPhoneNumber(userDTO.getPhoneNumber());
        updateUserRequest.setToReceiveNewsletter(userDTO.isToReceiveNewsletter());
        updateUserRequest.setEmail(userDTO.getEmail());
        return updateUserRequest;
    }

}