package hu.indicium.dev.ledenadministratie.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import hu.indicium.dev.ledenadministratie.registration.requests.FinishRegistrationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private RegistrationDTO registrationDTO;

    @BeforeEach
    void setUp() {
        registrationDTO = new RegistrationDTO();
        registrationDTO.setId(1L);
        registrationDTO.setFirstName("John");
        registrationDTO.setMiddleName("Daniel");
        registrationDTO.setLastName("Doe");
        registrationDTO.setMailAddress("John@doe.com");
        registrationDTO.setPhoneNumber("0612345678");
        registrationDTO.setStudyTypeId(1L);
        registrationDTO.setToReceiveNewsletter(true);
        registrationDTO.setDateOfBirth(new Date());
    }

    @Test
    @DisplayName("Register user")
    void shouldCreateRegistration_whenUserRegisters() throws Exception {
        ArgumentCaptor<RegistrationDTO> registrationDTOArgumentCaptor = ArgumentCaptor.forClass(RegistrationDTO.class);

        CreateRegistrationRequest createRegistrationRequest = new CreateRegistrationRequest();
        createRegistrationRequest.setFirstName(registrationDTO.getFirstName());
        createRegistrationRequest.setMiddleName(registrationDTO.getMiddleName());
        createRegistrationRequest.setLastName(registrationDTO.getLastName());
        createRegistrationRequest.setStudyTypeId(registrationDTO.getStudyTypeId());
        createRegistrationRequest.setDateOfBirth(registrationDTO.getDateOfBirth());
        createRegistrationRequest.setPhoneNumber(registrationDTO.getPhoneNumber());
        createRegistrationRequest.setToReceiveNewsletter(registrationDTO.isToReceiveNewsletter());
        createRegistrationRequest.setMailAddress(registrationDTO.getMailAddress());

        when(registrationService.register(registrationDTOArgumentCaptor.capture())).thenReturn(registrationDTO);

        mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(createRegistrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.data.middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.data.lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.data.mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.data.studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.data.toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.CREATED.value())));

        RegistrationDTO capturedRegistrationDTO = registrationDTOArgumentCaptor.getValue();
        assertThat(capturedRegistrationDTO.getId()).isNull();
        assertThat(capturedRegistrationDTO.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(capturedRegistrationDTO.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(capturedRegistrationDTO.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(capturedRegistrationDTO.getMailAddress()).isEqualTo(registrationDTO.getMailAddress());
        assertThat(capturedRegistrationDTO.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(capturedRegistrationDTO.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(capturedRegistrationDTO.isApproved()).isFalse();
        assertThat(capturedRegistrationDTO.getFinalizedAt()).isNull();
        assertThat(capturedRegistrationDTO.getFinalizedBy()).isNull();
        assertThat(capturedRegistrationDTO.getComment()).isNull();
    }

    @Test
    @DisplayName("Get registrations")
    void shouldReturnJsonArrayOfRegistrations() throws Exception {
        when(registrationService.getRegistrations()).thenReturn(Collections.singletonList(registrationDTO));

        mvc.perform(get("/registration")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.data[0].middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.data[0].lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.data[0].mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.data[0].studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.data[0].toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.data[0].approved", is(registrationDTO.isApproved())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
    }

    @Test
    @DisplayName("Get unfinalized registrations")
    void shouldReturnJsonArrayOfUnfinalizedRegistrations() throws Exception {

        when(registrationService.getRegistrationByFinalization(eq(false))).thenReturn(Collections.singletonList(registrationDTO));

        mvc.perform(get("/registration?finalized=false")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.data[0].middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.data[0].lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.data[0].mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.data[0].studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.data[0].toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.data[0].approved", is(registrationDTO.isApproved())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
    }

    @Test
    @DisplayName("Get finalized registrations")
    void shouldReturnJsonArrayOfFinalizedRegistrations() throws Exception {
        registrationDTO.setApproved(true);

        when(registrationService.getRegistrationByFinalization(eq(true))).thenReturn(Collections.singletonList(registrationDTO));

        mvc.perform(get("/registration?finalized=true")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.data[0].firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.data[0].middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.data[0].lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.data[0].mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.data[0].studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.data[0].toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.data[0].approved", is(registrationDTO.isApproved())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.OK.value())));
    }

    @Test
    @DisplayName("Finalize registration")
    void shouldFinalizeRegistration() throws Exception {
        ArgumentCaptor<FinishRegistrationDTO> finishRegistrationDTOArgumentCaptor = ArgumentCaptor.forClass(FinishRegistrationDTO.class);

        FinishRegistrationRequest finishRegistrationRequest = new FinishRegistrationRequest();
        finishRegistrationRequest.setApproved(true);
        finishRegistrationRequest.setComment(null);

        System.out.println(objectMapper.writer().writeValueAsString(finishRegistrationRequest));

        when(registrationService.finalizeRegistration(finishRegistrationDTOArgumentCaptor.capture())).thenReturn(registrationDTO);

        mvc.perform(post("/registration/5/finalize")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(finishRegistrationRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.data.middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.data.lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.data.mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.data.studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.data.toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.data.approved", is(registrationDTO.isApproved())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.ACCEPTED.value())));

        FinishRegistrationDTO capturedFinishRegistrationDTO = finishRegistrationDTOArgumentCaptor.getValue();
        assertThat(capturedFinishRegistrationDTO.getRegistrationId()).isEqualTo(5L);
        assertThat(capturedFinishRegistrationDTO.isApproved()).isTrue();
        assertThat(capturedFinishRegistrationDTO.getComment()).isNull();
    }

    @Test
    @DisplayName("Finalize registration with a negative outcome")
    void shouldFinalizeRegistration_withNegativeOutcome() throws Exception {
        ArgumentCaptor<FinishRegistrationDTO> finishRegistrationDTOArgumentCaptor = ArgumentCaptor.forClass(FinishRegistrationDTO.class);

        registrationDTO.setComment("troll");

        FinishRegistrationRequest finishRegistrationRequest = new FinishRegistrationRequest();
        finishRegistrationRequest.setApproved(false);
        finishRegistrationRequest.setComment(registrationDTO.getComment());

        when(registrationService.finalizeRegistration(finishRegistrationDTOArgumentCaptor.capture())).thenReturn(registrationDTO);

        mvc.perform(post("/registration/1/finalize")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(finishRegistrationRequest)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.data.firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.data.middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.data.lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.data.mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.data.studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.data.toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.data.comment", is(registrationDTO.getComment())))
                .andExpect(jsonPath("$.data.approved", is(registrationDTO.isApproved())))
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.status", is(HttpStatus.ACCEPTED.value())));

        FinishRegistrationDTO capturedFinishRegistrationDTO = finishRegistrationDTOArgumentCaptor.getValue();
        assertThat(capturedFinishRegistrationDTO.getRegistrationId()).isEqualTo(registrationDTO.getId());
        assertThat(capturedFinishRegistrationDTO.isApproved()).isFalse();
        assertThat(capturedFinishRegistrationDTO.getComment()).isEqualTo(registrationDTO.getComment());
    }
}