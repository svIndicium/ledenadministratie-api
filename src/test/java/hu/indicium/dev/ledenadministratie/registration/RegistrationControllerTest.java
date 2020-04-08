package hu.indicium.dev.ledenadministratie.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import hu.indicium.dev.ledenadministratie.registration.requests.FinishRegistrationRequest;
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

import java.util.Collections;
import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
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
                .andExpect(jsonPath("$.mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())));

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
        RegistrationDTO registrationDTO = getRegistrationDTO();
        registrationDTO.setId(1L);

        when(registrationService.getRegistrations()).thenReturn(Collections.singletonList(registrationDTO));

        mvc.perform(get("/registration")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$[0].middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$[0].lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$[0].mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$[0].studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$[0].toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())));
    }

    @Test
    @DisplayName("Get unfinalized registrations")
    void shouldReturnJsonArrayOfUnfinalizedRegistrations() throws Exception {
        RegistrationDTO registrationDTO = getRegistrationDTO();
        registrationDTO.setId(1L);

        when(registrationService.getRegistrationByFinalization(eq(false))).thenReturn(Collections.singletonList(registrationDTO));

        mvc.perform(get("/registration?finalized=false")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$[0].middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$[0].lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$[0].mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$[0].studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$[0].toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$[0].approved", is(false)))
                .andExpect(jsonPath("$[0].comment").doesNotExist());
    }

    @Test
    @DisplayName("Get finalized registrations")
    void shouldReturnJsonArrayOfFinalizedRegistrations() throws Exception {
        RegistrationDTO registrationDTO = getRegistrationDTO();
        registrationDTO.setApproved(true);
        registrationDTO.setId(1L);

        when(registrationService.getRegistrationByFinalization(eq(true))).thenReturn(Collections.singletonList(registrationDTO));

        mvc.perform(get("/registration?finalized=true")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$[0].middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$[0].lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$[0].mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$[0].studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$[0].toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$[0].approved", is(true)))
                .andExpect(jsonPath("$[0].comment").doesNotExist());
    }

    @Test
    @DisplayName("Finalize registration")
    void shouldFinalizeRegistration() throws Exception {
        ArgumentCaptor<FinishRegistrationDTO> finishRegistrationDTOArgumentCaptor = ArgumentCaptor.forClass(FinishRegistrationDTO.class);

        FinishRegistrationDTO finishRegistrationDTO = new FinishRegistrationDTO(5L, null, true);

        FinishRegistrationRequest finishRegistrationRequest = toFinishRegistrationRequest(finishRegistrationDTO);

        RegistrationDTO registrationDTO = getRegistrationDTO();
        registrationDTO.setApproved(finishRegistrationDTO.isApproved());
        registrationDTO.setComment(finishRegistrationDTO.getComment());
        registrationDTO.setId(5L);
        finishRegistrationDTO.setRegistrationId(5L);

        when(registrationService.finalizeRegistration(finishRegistrationDTOArgumentCaptor.capture())).thenReturn(registrationDTO);

        mvc.perform(post("/registration/5/finalize")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(finishRegistrationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.comment").doesNotExist())
                .andExpect(jsonPath("$.approved", is(true)));

        FinishRegistrationDTO capturedFinishRegistrationDTO = finishRegistrationDTOArgumentCaptor.getValue();
        assertThat(capturedFinishRegistrationDTO.getRegistrationId()).isEqualTo(finishRegistrationDTO.getRegistrationId());
        assertThat(capturedFinishRegistrationDTO.isApproved()).isTrue();
        assertThat(capturedFinishRegistrationDTO.getComment()).isNull();
    }

    @Test
    @DisplayName("Finalize registration with a negative outcome")
    void shouldFinalizeRegistration_withNegativeOutcome() throws Exception {
        ArgumentCaptor<FinishRegistrationDTO> finishRegistrationDTOArgumentCaptor = ArgumentCaptor.forClass(FinishRegistrationDTO.class);

        FinishRegistrationDTO finishRegistrationDTO = new FinishRegistrationDTO(5L, "null", false);

        FinishRegistrationRequest finishRegistrationRequest = toFinishRegistrationRequest(finishRegistrationDTO);

        RegistrationDTO registrationDTO = getRegistrationDTO();
        registrationDTO.setApproved(finishRegistrationDTO.isApproved());
        registrationDTO.setComment(finishRegistrationDTO.getComment());
        registrationDTO.setId(5L);
        finishRegistrationDTO.setRegistrationId(5L);

        when(registrationService.finalizeRegistration(finishRegistrationDTOArgumentCaptor.capture())).thenReturn(registrationDTO);

        mvc.perform(post("/registration/5/finalize")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(user("user"))
                .with(csrf())
                .content(objectMapper.writer().writeValueAsString(finishRegistrationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(registrationDTO.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(registrationDTO.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(registrationDTO.getMiddleName())))
                .andExpect(jsonPath("$.lastName", is(registrationDTO.getLastName())))
                .andExpect(jsonPath("$.mailAddress", is(registrationDTO.getMailAddress())))
                .andExpect(jsonPath("$.studyTypeId", is(registrationDTO.getStudyTypeId().intValue())))
                .andExpect(jsonPath("$.toReceiveNewsletter", is(registrationDTO.isToReceiveNewsletter())))
                .andExpect(jsonPath("$.comment", is(finishRegistrationDTO.getComment())))
                .andExpect(jsonPath("$.approved", is(false)));

        FinishRegistrationDTO capturedFinishRegistrationDTO = finishRegistrationDTOArgumentCaptor.getValue();
        assertThat(capturedFinishRegistrationDTO.getRegistrationId()).isEqualTo(finishRegistrationDTO.getRegistrationId());
        assertThat(capturedFinishRegistrationDTO.isApproved()).isFalse();
        assertThat(capturedFinishRegistrationDTO.getComment()).isEqualTo(finishRegistrationDTO.getComment());
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
        registration.setMailAddress("John@doe.com");
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
        registrationDTO.setMailAddress(registration.getMailAddress());
        registrationDTO.setPhoneNumber(registration.getPhoneNumber());
        registrationDTO.setStudyTypeId(registration.getStudyType().getId());
        registrationDTO.setToReceiveNewsletter(registration.isToReceiveNewsletter());
        registrationDTO.setDateOfBirth(registration.getDateOfBirth());
        return registrationDTO;
    }

    private CreateRegistrationRequest toCreateRegistrationRequest(RegistrationDTO registrationDTO) {
        CreateRegistrationRequest createRegistrationRequest = new CreateRegistrationRequest();
        createRegistrationRequest.setFirstName(registrationDTO.getFirstName());
        createRegistrationRequest.setMiddleName(registrationDTO.getMiddleName());
        createRegistrationRequest.setLastName(registrationDTO.getLastName());
        createRegistrationRequest.setStudyTypeId(registrationDTO.getStudyTypeId());
        createRegistrationRequest.setDateOfBirth(registrationDTO.getDateOfBirth());
        createRegistrationRequest.setPhoneNumber(registrationDTO.getPhoneNumber());
        createRegistrationRequest.setToReceiveNewsletter(registrationDTO.isToReceiveNewsletter());
        createRegistrationRequest.setMailAddress(registrationDTO.getMailAddress());
        return createRegistrationRequest;
    }

    private FinishRegistrationRequest toFinishRegistrationRequest(FinishRegistrationDTO finishRegistrationDTO) {
        FinishRegistrationRequest finishRegistrationRequest = new FinishRegistrationRequest();
        finishRegistrationRequest.setApproved(finishRegistrationDTO.isApproved());
        finishRegistrationRequest.setComment(finishRegistrationDTO.getComment());
        return finishRegistrationRequest;
    }
}