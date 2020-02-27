package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.auth.AuthService;
import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.UserService;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Registration Service")
class RegistrationServiceImplTest {

    @MockBean
    private RegistrationRepository registrationRepository;

    @MockBean
    private Mapper<Registration, RegistrationDTO> registrationMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private Validator<Registration> registrationValidator;

    @MockBean
    private AuthService authService;

    @MockBean
    private RegistrationUserMapper registrationUserMapper;

    @Autowired
    private RegistrationService registrationService;

    private StudyType studyType;

    private StudyTypeDTO studyTypeDTO;

    private Registration registration;

    private RegistrationDTO registrationDTO;

    private AuthUserDTO authUserDTO;

    @BeforeEach
    void setUp() {
        studyType = new StudyType("SD");
        studyType.setId(1L);

        studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(studyType.getId());
        studyTypeDTO.setName(studyType.getName());

        registration = new Registration();
        registration.setFirstName("John");
        registration.setLastName("Doe");
        registration.setEmail("john@doe.com");
        registration.setStudyType(studyType);
        registration.setPhoneNumber("+31612345678");
        registration.setToReceiveNewsletter(true);
        registration.setDateOfBirth(new Date());
        registration.setApproved(false);

        registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName(registration.getFirstName());
        registrationDTO.setLastName(registration.getLastName());
        registrationDTO.setEmail(registration.getEmail());
        registrationDTO.setPhoneNumber(registration.getPhoneNumber());
        registrationDTO.setToReceiveNewsletter(registration.isToReceiveNewsletter());
        registrationDTO.setDateOfBirth(registration.getDateOfBirth());
        registrationDTO.setApproved(registration.isApproved());

        authUserDTO = new AuthUserDTO();
        authUserDTO.setName("Alex");

    }

    @Test
    @DisplayName("Register user")
    void shouldCreateRegistration() {
        ArgumentCaptor<Registration> registrationArgument = ArgumentCaptor.forClass(Registration.class);

        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        when(registrationMapper.toEntity(any(RegistrationDTO.class))).thenReturn(registration);
        when(registrationMapper.toDTO(any(Registration.class))).thenReturn(registrationDTO);

        RegistrationDTO returnedRegistration = registrationService.register(registrationDTO);

        verify(registrationRepository, times(1)).save(registrationArgument.capture());
        verify(registrationValidator, atLeastOnce()).validate(any(Registration.class));

        Registration savedRegistration = registrationArgument.getValue();

        assertThat(savedRegistration.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(savedRegistration.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(savedRegistration.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(savedRegistration.getEmail()).isEqualTo(registrationDTO.getEmail());
        assertThat(savedRegistration.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(savedRegistration.getDateOfBirth()).isEqualTo(registrationDTO.getDateOfBirth());
        assertThat(savedRegistration.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(returnedRegistration).isEqualToComparingFieldByField(registrationDTO);
    }

    @Test
    @DisplayName("Register user with more data that shouldn't be persisted")
    void shouldCreateRegistration_withoutExtraData() {
        ArgumentCaptor<Registration> registrationArgument = ArgumentCaptor.forClass(Registration.class);

        registration.setApproved(true);
        registration.setComment("kek");
        registration.setId(2L);
        registration.setFinalizedAt(new Date());
        registration.setFinalizedBy("Alex");

        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        when(registrationMapper.toEntity(any(RegistrationDTO.class))).thenReturn(registration);
        when(registrationMapper.toDTO(any(Registration.class))).thenReturn(registrationDTO);

        RegistrationDTO returnedRegistration = registrationService.register(registrationDTO);

        verify(registrationRepository, times(1)).save(registrationArgument.capture());
        verify(registrationValidator, atLeastOnce()).validate(any(Registration.class));

        Registration savedRegistration = registrationArgument.getValue();

        assertThat(savedRegistration.getId()).isNull();
        assertThat(savedRegistration.isApproved()).isFalse();
        assertThat(savedRegistration.getComment()).isNull();
        assertThat(savedRegistration.getFinalizedAt()).isNull();
        assertThat(savedRegistration.getFinalizedBy()).isNull();
        assertThat(returnedRegistration).isEqualToComparingFieldByField(registrationDTO);
    }

    @Test
    @DisplayName("Finalize registration")
    void shouldCreateUser_whenFinalizeRegistration() {
        ArgumentCaptor<Registration> registrationArgument = ArgumentCaptor.forClass(Registration.class);

        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        when(registrationRepository.findById(any(Long.class))).thenReturn(Optional.of(registration));
        when(registrationMapper.toEntity(any(RegistrationDTO.class))).thenReturn(registration);
        when(registrationMapper.toDTO(any(Registration.class))).thenReturn(registrationDTO);
        when(authService.getAuthUser()).thenReturn(authUserDTO);

        FinishRegistrationDTO finishRegistrationDTO = new FinishRegistrationDTO(1L, null, true);

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(registrationDTO.getFirstName());
        userDTO.setMiddleName(registrationDTO.getMiddleName());
        userDTO.setLastName(registrationDTO.getLastName());
        userDTO.setEmail(registrationDTO.getEmail());
        userDTO.setPhoneNumber(registrationDTO.getPhoneNumber());
        userDTO.setDateOfBirth(registrationDTO.getDateOfBirth());
        userDTO.setStudyType(studyTypeDTO);
        userDTO.setToReceiveNewsletter(registrationDTO.isToReceiveNewsletter());

        when(registrationUserMapper.toDTO(any(RegistrationDTO.class))).thenReturn(userDTO);

        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        RegistrationDTO returnedRegistration = registrationService.finalizeRegistration(finishRegistrationDTO);

        verify(registrationRepository, times(1)).save(registrationArgument.capture());
        verify(registrationValidator, atLeastOnce()).validate(any(Registration.class));
        verify(userService, times(1)).createUser(any(UserDTO.class));

        Registration savedRegistration = registrationArgument.getValue();

        assertThat(savedRegistration.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(savedRegistration.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(savedRegistration.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(savedRegistration.getEmail()).isEqualTo(registrationDTO.getEmail());
        assertThat(savedRegistration.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(savedRegistration.getDateOfBirth()).isEqualTo(registrationDTO.getDateOfBirth());
        assertThat(savedRegistration.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(savedRegistration.isApproved()).isEqualTo(finishRegistrationDTO.isApproved());
        assertThat(savedRegistration.getComment()).isEqualTo(finishRegistrationDTO.getComment());
        assertThat(savedRegistration.getFinalizedAt()).isEqualToIgnoringMinutes(new Date());
        assertThat(savedRegistration.getFinalizedBy()).isEqualTo(authUserDTO.getName());
        assertThat(returnedRegistration).isEqualToComparingFieldByField(registrationDTO);
    }

    @Test
    @DisplayName("Finalize registration by declining")
    void shouldNotCreateUser_whenFinalizeRegistration_ifUserIsDeclined() {
        ArgumentCaptor<Registration> registrationArgument = ArgumentCaptor.forClass(Registration.class);

        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        when(registrationRepository.findById(any(Long.class))).thenReturn(Optional.of(registration));
        when(registrationMapper.toEntity(any(RegistrationDTO.class))).thenReturn(registration);
        when(registrationMapper.toDTO(any(Registration.class))).thenReturn(registrationDTO);
        when(authService.getAuthUser()).thenReturn(authUserDTO);

        FinishRegistrationDTO finishRegistrationDTO = new FinishRegistrationDTO(1L, "Troll", false);

        RegistrationDTO returnedRegistration = registrationService.finalizeRegistration(finishRegistrationDTO);

        verify(registrationRepository, times(1)).save(registrationArgument.capture());
        verify(registrationValidator, atLeastOnce()).validate(any(Registration.class));
        verify(userService, never()).createUser(any(UserDTO.class));

        Registration savedRegistration = registrationArgument.getValue();

        assertThat(savedRegistration.getFirstName()).isEqualTo(registrationDTO.getFirstName());
        assertThat(savedRegistration.getMiddleName()).isEqualTo(registrationDTO.getMiddleName());
        assertThat(savedRegistration.getLastName()).isEqualTo(registrationDTO.getLastName());
        assertThat(savedRegistration.getEmail()).isEqualTo(registrationDTO.getEmail());
        assertThat(savedRegistration.getPhoneNumber()).isEqualTo(registrationDTO.getPhoneNumber());
        assertThat(savedRegistration.getDateOfBirth()).isEqualTo(registrationDTO.getDateOfBirth());
        assertThat(savedRegistration.isToReceiveNewsletter()).isEqualTo(registrationDTO.isToReceiveNewsletter());
        assertThat(savedRegistration.isApproved()).isEqualTo(finishRegistrationDTO.isApproved());
        assertThat(savedRegistration.getComment()).isEqualTo(finishRegistrationDTO.getComment());
        assertThat(savedRegistration.getFinalizedAt()).isEqualToIgnoringMinutes(new Date());
        assertThat(savedRegistration.getFinalizedBy()).isEqualTo(authUserDTO.getName());
        assertThat(returnedRegistration).isEqualToComparingFieldByField(registrationDTO);
    }

    @Test
    @DisplayName("Finalize registration of non-existant user should fail")
    void shouldNotCreateUser_whenFinalizeRegistration_ifUserDoesNotExist() {
        when(registrationRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        FinishRegistrationDTO finishRegistrationDTO = new FinishRegistrationDTO(1L, "Troll", false);

        try {
            RegistrationDTO returnedRegistration = registrationService.finalizeRegistration(finishRegistrationDTO);
            fail("User does not exist, thus should throw an exception");
        } catch (EntityNotFoundException e) {
            assertThat(e.getMessage()).isEqualTo("Entity with ID 1 not found!");
        }

        verify(registrationRepository, times(0)).save(any(Registration.class));
        verify(userService, never()).createUser(any(UserDTO.class));
    }

    @Test
    @DisplayName("Get registrations")
    void shouldReturnListOfRegistrations() {
        when(registrationRepository.findAll()).thenReturn(Arrays.asList(registration, registration));
        when(registrationMapper.toDTO(eq(registration))).thenReturn(registrationDTO);

        List<RegistrationDTO> returnedRegistrations = registrationService.getRegistrations();

        assertThat(returnedRegistrations).hasSize(2);
        assertThat(returnedRegistrations).contains(registrationDTO);
    }

    @Test
    @DisplayName("Get Unfinalized registrations")
    void shouldReturnListOfUnfinalizedRegistrations() {
        when(registrationRepository.findAllByApprovedIsFalseAndCommentIsNull()).thenReturn(Arrays.asList(registration, registration));
        when(registrationMapper.toDTO(eq(registration))).thenReturn(registrationDTO);

        List<RegistrationDTO> returnedRegistrations = registrationService.getRegistrationByFinalization(false);

        assertThat(returnedRegistrations).hasSize(2);
        assertThat(returnedRegistrations).contains(registrationDTO);
    }

    @Test
    @DisplayName("Get finalized registrations")
    void shouldReturnListOfFinalizedRegistrations() {
        when(registrationRepository.findAllByApprovedIsTrueOrApprovedIsFalseAndCommentIsNotNull()).thenReturn(Arrays.asList(registration, registration));
        when(registrationMapper.toDTO(eq(registration))).thenReturn(registrationDTO);

        List<RegistrationDTO> returnedRegistrations = registrationService.getRegistrationByFinalization(true);

        assertThat(returnedRegistrations).hasSize(2);
        assertThat(returnedRegistrations).contains(registrationDTO);
    }


    @TestConfiguration
    static class RegistrationServiceTestContextConfiguration {
        @Autowired
        private RegistrationRepository registrationRepository;

        @Autowired
        private Mapper<Registration, RegistrationDTO> registrationMapper;

        @Autowired
        private UserService userService;

        @Autowired
        private Validator<Registration> registrationValidator;

        @Autowired
        private AuthService authService;

        @Autowired
        private RegistrationUserMapper registrationUserMapper;

        @Bean
        public RegistrationService registrationService() {
            return new RegistrationServiceImpl(registrationRepository, registrationMapper, userService, registrationValidator, authService, registrationUserMapper);
        }
    }
}