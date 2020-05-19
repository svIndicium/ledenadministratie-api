package hu.indicium.dev.ledenadministratie.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.requests.UpdateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private UserDTO userDTO;

    private MailAddressDTO mailAddressDTO;

    @BeforeEach
    void setUp() {
        StudyType studyType = new StudyType();
        studyType.setId(1L);
        studyType.setName("Software Development");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setMiddleName("Daniel");
        userDTO.setLastName("Doe");
        userDTO.setPhoneNumber("0612345678");
        userDTO.setStudyTypeId(studyType.getId());
        userDTO.setDateOfBirth(new Date());

        mailAddressDTO = new MailAddressDTO();
        mailAddressDTO.setId(0L);
        mailAddressDTO.setAddress("john@doe.com");
        mailAddressDTO.setVerified(true);
        mailAddressDTO.setVerificationRequestedAt(new Date());
        mailAddressDTO.setReceivesNewsletter(true);
    }

    @Test
    @DisplayName("Get single user")
    void shouldReturnSingleUser_whenGetUserById() throws Exception {

        given(userService.getUserById(eq(1L))).willReturn(userDTO);

        mvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.data.middleName", is(userDTO.getMiddleName())))
                .andExpect(jsonPath("$.data.lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.data.phoneNumber", is(userDTO.getPhoneNumber())))
                .andExpect(jsonPath("$.data.studyTypeId", is(userDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("Get all users")
    void shouldReturnListOfUsers_whenGetAllUsers() throws Exception {

        given(userService.getUsers()).willReturn(Arrays.asList(userDTO, userDTO));

        mvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].id", is(userDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.data[0].middleName", is(userDTO.getMiddleName())))
                .andExpect(jsonPath("$.data[0].lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.data[0].phoneNumber", is(userDTO.getPhoneNumber())))
                .andExpect(jsonPath("$.data[0].studyTypeId", is(userDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.data[1].id", is(userDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[1].firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.data[1].middleName", is(userDTO.getMiddleName())))
                .andExpect(jsonPath("$.data[1].lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.data[1].phoneNumber", is(userDTO.getPhoneNumber())))
                .andExpect(jsonPath("$.data[1].studyTypeId", is(userDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
    }

    @Test
    @DisplayName("Update user")
    void shouldReturnUpdatedUser_whenUpdateUser() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();

        given(userService.updateUser(any(UserDTO.class))).willReturn(userDTO);

        mvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(updateUserRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.firstName", is(userDTO.getFirstName())))
                .andExpect(jsonPath("$.data.middleName", is(userDTO.getMiddleName())))
                .andExpect(jsonPath("$.data.lastName", is(userDTO.getLastName())))
                .andExpect(jsonPath("$.data.phoneNumber", is(userDTO.getPhoneNumber())))
                .andExpect(jsonPath("$.data.studyTypeId", is(userDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.ACCEPTED.value())));

        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Get mailaddresses by user id")
    void shouldReturnJsonObject_whenGetMailAddressesByUserId() throws Exception {
        given(userService.getMailAddressesByUserId(eq(1L))).willReturn(Collections.singletonList(mailAddressDTO));

        mvc.perform(get("/api/v1/users/1/mailaddresses")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(mailAddressDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].address", is(mailAddressDTO.getAddress())))
                .andExpect(jsonPath("$.data[0].verified", is(mailAddressDTO.isVerified())))
                .andExpect(jsonPath("$.data[0].receivesNewsletter", is(mailAddressDTO.isReceivesNewsletter())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
    }

    @Test
    @DisplayName("Request new mail verification")
    void shouldReturnJsonObject_whenRequestNewMailVerification() throws Exception {
        given(userService.requestNewMailVerification(eq(1L), eq(0L))).willReturn(mailAddressDTO);

        mvc.perform(get("/api/v1/users/1/mailaddresses/0/requestverification")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(mailAddressDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.address", is(mailAddressDTO.getAddress())))
                .andExpect(jsonPath("$.data.verified", is(mailAddressDTO.isVerified())))
                .andExpect(jsonPath("$.data.receivesNewsletter", is(mailAddressDTO.isReceivesNewsletter())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.ACCEPTED.value())));
    }
}