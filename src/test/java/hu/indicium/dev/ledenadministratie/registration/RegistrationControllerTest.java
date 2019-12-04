package hu.indicium.dev.ledenadministratie.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RegistrationController.class)
@DisplayName("Registration controller")
@Tag("Controller")
class RegistrationControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Register user")
    void shouldCreateRegistration_whenUserRegisters() throws Exception {
        ArgumentCaptor<RegistrationDTO> registrationDTOArgumentCaptor = ArgumentCaptor.forClass(RegistrationDTO.class);

        RegistrationDTO registrationDTO = getRegistrationDTO();

        CreateRegistrationRequest createRegistrationRequest = toCreateRegistrationRequest(registrationDTO);

        registrationDTO.setId(5L);

        when(registrationService.register(registrationDTOArgumentCaptor.capture())).thenReturn(registrationDTO);

        mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createRegistrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.email", is(registrationDTO.getEmail())))
                .andExpect(jsonPath("$.studyType", notNullValue()))
                .andExpect(jsonPath("$.studyType.id", is(1)))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())));

        RegistrationDTO capturedRegistrationDTO = registrationDTOArgumentCaptor.getValue();
        assertThat(capturedRegistrationDTO.getId()).isNull();
        assertThat(capturedRegistrationDTO.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(capturedRegistrationDTO.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(capturedRegistrationDTO.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(capturedRegistrationDTO.getEmail()).isEqualTo(registrationDTO.getEmail());
        assertThat(capturedRegistrationDTO.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(capturedRegistrationDTO.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(capturedRegistrationDTO.isApproved()).isFalse();
        assertThat(capturedRegistrationDTO.getFinalizedAt()).isNull();
        assertThat(capturedRegistrationDTO.getFinalizedBy()).isNull();
        assertThat(capturedRegistrationDTO.getComment()).isNull();
    }


    private StudyType getStudyType() {
        StudyType studyType = new StudyType("Software Development");
        studyType.setId(1L);
        return studyType;
    }

    private StudyTypeDTO getStudyTypeDTO() {
        StudyType studyType = getStudyType();
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());
        return studyTypeDTO;
    }

    private Registration getRegistration() {
        Registration registration = new Registration();
        registration.setFirstName("John");
        registration.setMiddleName("Daniel");
        registration.setLastName("Doe");
        registration.setEmail("John@doe.com");
        registration.setPhoneNumber("0612345678");
        registration.setStudyType(getStudyType());
        registration.setToReceiveNewsletter(true);
        registration.setDateOfBirth(new Date());
        return registration;
    }

    private RegistrationDTO getRegistrationDTO() {
        StudyTypeDTO studyTypeDTO = getStudyTypeDTO();
        Registration registration = getRegistration();
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName(registration.getFirstName());
        registrationDTO.setMiddleName(registration.getMiddleName());
        registrationDTO.setLastName(registration.getLastName());
        registrationDTO.setEmail(registration.getEmail());
        registrationDTO.setPhoneNumber(registration.getPhoneNumber());
        registrationDTO.setStudyType(studyTypeDTO);
        registrationDTO.setToReceiveNewsletter(registration.isToReceiveNewsletter());
        registrationDTO.setDateOfBirth(registration.getDateOfBirth());
        return registrationDTO;
    }

    private CreateRegistrationRequest toCreateRegistrationRequest(RegistrationDTO registrationDTO) {
        CreateRegistrationRequest createRegistrationRequest = new CreateRegistrationRequest();
        createRegistrationRequest.setFirstName(registrationDTO.getFirstName());
        createRegistrationRequest.setMiddleName(registrationDTO.getMiddleName());
        createRegistrationRequest.setLastName(registrationDTO.getLastName());
        createRegistrationRequest.setStudyTypeId(registrationDTO.getStudyType().getId());
        createRegistrationRequest.setDateOfBirth(registrationDTO.getDateOfBirth());
        createRegistrationRequest.setPhoneNumber(registrationDTO.getPhoneNumber());
        createRegistrationRequest.setToReceiveNewsletter(registrationDTO.isToReceiveNewsletter());
        createRegistrationRequest.setEmail(registrationDTO.getEmail());
        return createRegistrationRequest;
    }
}