package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.auth.AuthService;
import hu.indicium.dev.ledenadministratie.mail.MailObject;
import hu.indicium.dev.ledenadministratie.mail.MailService;
import hu.indicium.dev.ledenadministratie.mail.dto.TransactionalMailDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {UserServiceImpl.class, ModelMapper.class})
@DisplayName("User Service")
@Tag("Services")
class UserServiceImplTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Validator<User> userValidator;

    @MockBean
    private MailService mailService;

    @MockBean
    private MailAddressRepository mailAddressRepository;

    @MockBean
    private StudyTypeService studyTypeService;

    @MockBean
    private AuthService authService;

    @MockBean
    private SettingService settingService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    private User user;

    private UserDTO userDTO;

    private MailAddress mailAddress;

    private MailAddressDTO mailAddressDTO;

    private StudyType studyType;

    private RegistrationDTO registrationDTO;


    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setMiddleName("Daniel");
        user.setLastName("Doe");
        user.setPhoneNumber("+31612345678");
        user.setDateOfBirth(new Date());

        mailAddress = new MailAddress();
        mailAddress.setId(0L);
        mailAddress.setMailAddress("john@doe.com");
        mailAddress.setReceivesNewsletter(true);
        mailAddress.setVerificationToken("ASBD");
        mailAddress.setVerificationRequestedAt(new Date());
        mailAddress.setUser(user);

        user.addMailAddress(mailAddress);

        studyType = new StudyType();
        studyType.setId(1L);
        studyType.setName("Software Development");

        user.setStudyType(studyType);

        userDTO = new UserDTO();
        userDTO.setFirstName(user.getFirstName());
        userDTO.setMiddleName(user.getMiddleName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setStudyTypeId(user.getStudyType().getId());

        mailAddressDTO = new MailAddressDTO();
        mailAddressDTO.setId(mailAddress.getId());
        mailAddressDTO.setAddress(mailAddress.getMailAddress());
        mailAddressDTO.setReceivesNewsletter(mailAddress.receivesNewsletter());
        mailAddressDTO.setVerifiedAt(mailAddress.getVerifiedAt());
        mailAddressDTO.setVerified(mailAddress.getVerifiedAt() != null);
        mailAddressDTO.setVerificationRequestedAt(mailAddress.getVerificationRequestedAt());

        registrationDTO = new RegistrationDTO();
        registrationDTO.setId(1L);
        registrationDTO.setFirstName(user.getFirstName());
        registrationDTO.setMiddleName(user.getMiddleName());
        registrationDTO.setLastName(user.getLastName());
        registrationDTO.setPhoneNumber(user.getPhoneNumber());
        registrationDTO.setDateOfBirth(user.getDateOfBirth());
        registrationDTO.setMailAddress(mailAddress.getMailAddress());
        registrationDTO.setVerificationToken(mailAddress.getVerificationToken());
        registrationDTO.setToReceiveNewsletter(mailAddress.receivesNewsletter());
        registrationDTO.setVerificationRequestedAt(mailAddress.getVerificationRequestedAt());
        registrationDTO.setStudyTypeId(studyType.getId());
    }

    @Test
    @DisplayName("Create user")
    void createUser() {
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        when(userRepository.save(userArgumentCaptor.capture())).thenReturn(user);
        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));

        UserDTO returnedUserDTO = userService.createUser(registrationDTO);

        verify(userValidator, atLeastOnce()).validate(any(User.class));

        assertThat(returnedUserDTO).isNotNull();

        User savedUser = userArgumentCaptor.getValue();

        assertThat(savedUser).isEqualToIgnoringGivenFields(user, "id", "mailAddresses", "studyType");
    }

    @Test
    @DisplayName("Create invalid user should not be persisted")
    void creatingUserFails_shouldNotBePersisted() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        doThrow(new IllegalArgumentException("User not legitimate")).when(userValidator).validate(any(User.class));

        UserDTO returnedUserDTO = null;

        try {
            returnedUserDTO = userService.createUser(registrationDTO);
            fail();
        } catch (Exception e) {
            assertThat(true).isTrue();
        }


        verify(userValidator, atLeastOnce()).validate(any(User.class));
        verify(userRepository, never()).save(eq(user));

        assertThat(returnedUserDTO).isNull();
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));

        UserDTO receivedUser = userService.getUserById(1L);

        assertThat(receivedUser).isEqualToIgnoringGivenFields(user, "id", "mailAddresses", "studyType", "studyTypeId", "userId");
        assertThat(receivedUser.getUserId()).isEqualTo(user.getAuth0UserId());

    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("John");
        user1.setMiddleName("Daniel");
        user1.setLastName("Doe");
        user1.setPhoneNumber("+31612345678");
        user1.setDateOfBirth(new Date());
        user1.setStudyType(new StudyType(1L));

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setFirstName(user1.getFirstName());
        userDTO1.setMiddleName(user1.getMiddleName());
        userDTO1.setLastName(user1.getLastName());
        userDTO1.setPhoneNumber(user1.getPhoneNumber());
        userDTO1.setDateOfBirth(user1.getDateOfBirth());
        userDTO1.setStudyTypeId(user1.getStudyType().getId());

        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user1));

        List<UserDTO> users = userService.getUsers();

        assertThat(users).hasSize(2);
        assertThat(users.get(0)).isEqualToIgnoringGivenFields(userDTO, "id");
        assertThat(users.get(1)).isEqualToIgnoringGivenFields(userDTO1, "id");
    }

    @Test
    @DisplayName("Throw exception when getting non-existing user")
    void shouldThrowException_whenGetNonExistingUserById() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        try {
            userService.getUserById(1L);
            fail();
        } catch (Exception ex) {
            assertThat(ex.getClass()).isEqualTo(EntityNotFoundException.class);
            assertThat(ex.getMessage()).isEqualTo("User 1 not found!");
        }
    }

    @Test
    @DisplayName("Request new email verification")
    void shouldCallMailServiceCorrectly_whenRequestMailVerification() {
        ArgumentCaptor<MailObject> mailObjectArgumentCaptor = ArgumentCaptor.forClass(MailObject.class);
        ArgumentCaptor<TransactionalMailDTO> transactionalMailDTOArgumentCaptor = ArgumentCaptor.forClass(TransactionalMailDTO.class);

        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(mailAddressRepository.findByUserIdAndId(eq(1L), eq(0L))).thenReturn(Optional.of(mailAddress));
        when(mailAddressRepository.save(any(MailAddress.class))).thenReturn(mailAddress);
        when(mailService.sendVerificationMail(mailObjectArgumentCaptor.capture(), transactionalMailDTOArgumentCaptor.capture())).thenReturn(mailAddress);

        MailAddressDTO mailAddressDTO = userService.requestNewMailVerification(1L, 0L);

        assertThat(mailAddressDTO.getAddress()).isEqualTo(mailAddress.getMailAddress());

        MailObject capturedMailObject = mailObjectArgumentCaptor.getValue();

        assertThat(capturedMailObject.getMailAddress()).isEqualTo(mailAddress.getMailAddress());

        TransactionalMailDTO transactionalMailDTO = transactionalMailDTOArgumentCaptor.getValue();

        assertThat(transactionalMailDTO.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(transactionalMailDTO.getLastName()).isEqualTo(user.getFullLastName());
    }

    @Test
    @DisplayName("Throw exception when ask for verication mail for already verified mailaddress")
    void shouldThrowException_whenRequestMailVerification_withAlreadyVerifiedMail() {
        mailAddress.verify();

        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(mailAddressRepository.findByUserIdAndId(eq(1L), eq(0L))).thenReturn(Optional.of(mailAddress));

        try {
            userService.requestNewMailVerification(1L, 0L);
            fail("Should throw exception because the email is already verified");
        } catch (Exception e) {
            assertThat(true).isTrue();
        }

        verify(userRepository, never()).save(any(User.class));
        verify(mailAddressRepository, never()).save(any(MailAddress.class));
    }

    @Test
    @DisplayName("Throw exception when ask for verication mail for already verified mailaddress")
    void shouldThrowException_whenGetInvalidMail() {

        when(userRepository.findById(eq(1L))).thenReturn(Optional.of(user));
        when(mailAddressRepository.findByUserIdAndId(eq(1L), eq(0L))).thenReturn(Optional.empty());

        try {
            userService.requestNewMailVerification(1L, 0L);
            fail("Should throw exception because the email does not exist");
        } catch (Exception e) {
            assertThat(true).isTrue();
        }

        verify(userRepository, never()).save(any(User.class));
        verify(mailAddressRepository, never()).save(any(MailAddress.class));
    }

    @Test
    @DisplayName("Get mail addresses by user id")
    void getMailAddressesByUserId() {
        when(mailAddressRepository.findAllByUserId(eq(1L))).thenReturn(Collections.singletonList(mailAddress));

        List<MailAddressDTO> mailAddresses = userService.getMailAddressesByUserId(1L);

        assertThat(mailAddresses).hasSize(1);
        assertThat(mailAddresses.get(0)).isEqualToComparingFieldByField(mailAddressDTO);
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private Validator<User> userValidator;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private MailService mailService;

        @Autowired
        private MailAddressRepository mailAddressRepository;

        @Autowired
        private ApplicationEventPublisher applicationEventPublisher;

        @Autowired
        private AuthService authService;

        @Autowired
        private SettingService settingService;

        @Bean
        public UserService userService() {
            return new UserServiceImpl(userRepository, userValidator, modelMapper, mailService, mailAddressRepository, applicationEventPublisher, authService, settingService);
        }
    }
}