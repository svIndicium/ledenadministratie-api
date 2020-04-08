package hu.indicium.dev.ledenadministratie.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
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
import static org.hamcrest.Matchers.hasSize;
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
                .andExpect(jsonPath("$.studyTypeId", is(updatedUserDTO.getStudyTypeId().intValue())));

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
                .andExpect(jsonPath("$.studyTypeId", is(userDTO.getStudyTypeId().intValue())));
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
                .andExpect(jsonPath("$[0].studyTypeId", is(userDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$[1].id", is(userDTO1.getId().intValue())))
                .andExpect(jsonPath("$[1].firstName", is(userDTO1.getFirstName())))
                .andExpect(jsonPath("$[1].middleName", is(userDTO1.getMiddleName())))
                .andExpect(jsonPath("$[1].lastName", is(userDTO1.getLastName())))
                .andExpect(jsonPath("$[1].studyTypeId", is(userDTO.getStudyTypeId().intValue())));
    }

    private MailAddress getMailAddress() {
        MailAddress mailAddress = new MailAddress();
        mailAddress.setMailAddress("john@doe.com");
        mailAddress.setVerificationRequestedAt(new Date());
        mailAddress.setVerificationToken("12345");
        mailAddress.setReceivesNewsletter(true);
        return mailAddress;
    }

    private MailAddressDTO getMailAddressDTO() {
        MailAddress mailAddress = getMailAddress();
        MailAddressDTO mailAddressDTO = new MailAddressDTO();
        mailAddressDTO.setAddress(mailAddress.getMailAddress());
        mailAddressDTO.setVerificationRequestedAt(mailAddress.getVerificationRequestedAt());
        mailAddressDTO.setVerifiedAt(mailAddress.getVerifiedAt());
        mailAddressDTO.setReceivesNewsletter(mailAddress.receivesNewsletter());
        return mailAddressDTO;
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
        user.setPhoneNumber("0612345678");
        user.setStudyType(getStudyType());
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
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setStudyTypeId(getStudyType().getId());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        return userDTO;
    }

    private UpdateUserRequest toUpdateUserRequest(UserDTO userDTO) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setFirstName(userDTO.getFirstName());
        updateUserRequest.setMiddleName(userDTO.getMiddleName());
        updateUserRequest.setLastName(userDTO.getLastName());
        updateUserRequest.setStudyTypeId(userDTO.getStudyTypeId());
        updateUserRequest.setDateOfBirth(userDTO.getDateOfBirth());
        updateUserRequest.setPhoneNumber(userDTO.getPhoneNumber());
        return updateUserRequest;
    }

}