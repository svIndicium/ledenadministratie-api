package hu.indicium.dev.lit.register;

import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.register.dto.TokenDTO;
import hu.indicium.dev.lit.user.UserServiceInterface;
import hu.indicium.dev.lit.user.dto.UserDTO;
import hu.indicium.dev.lit.util.Mapper;
import hu.indicium.dev.lit.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = RegistrationServiceImpl.class)
@DisplayName("Registration Service")
@Tag("Services")
class RegistrationServiceTest {

    @Autowired
    private RegistrationService registrationService;

    @MockBean
    private RegistrationRepository registrationRepository;

    @MockBean
    private UserServiceInterface userService;

    @MockBean
    private Validator<Token> tokenValidator;

    @MockBean
    private Validator<Registration> registrationValidator;

    @MockBean
    private Mapper<Token, TokenDTO> tokenMapper;

    @MockBean
    private Mapper<Registration, RegistrationDTO> registrationMapper;

    @Test
    @DisplayName("Start registration")
    void startRegister() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken("asd");
        Token token = new Token(tokenDTO.getToken());

        when(tokenMapper.toEntity(any(TokenDTO.class))).thenReturn(token);

        registrationService.startRegistration(tokenDTO);
        verify(tokenValidator).validate(ArgumentMatchers.eq(new Token(tokenDTO.getToken())));
        verify(registrationRepository).save(ArgumentMatchers.eq(new Registration(tokenDTO.getToken())));
    }

    @Test
    @DisplayName("Start registration with invalid token")
    void startRegister_doesNotSaveToken_ifTokenValidationThrowsException() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken("asd");
        Token token = new Token(tokenDTO.getToken());

        when(tokenMapper.toEntity(any(TokenDTO.class))).thenReturn(token);

        doThrow(IllegalArgumentException.class).when(tokenValidator).validate(any(Token.class));
        try {
            registrationService.startRegistration(tokenDTO);
            assert false;
        } catch (IllegalArgumentException ex) {
            verify(registrationRepository, never()).save(ArgumentMatchers.eq(new Registration(tokenDTO.getToken())));
        }
    }

    @Test
    @DisplayName("Fill registration with user-given info")
    void fillInRegistration() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setToken("asd");
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("john@doe.com");

        Registration registration = new Registration("asd");

        Registration filledRegistration = new Registration();
        filledRegistration.setToken(registrationDTO.getToken());
        filledRegistration.setFirstName(registrationDTO.getFirstName());
        filledRegistration.setLastName(registrationDTO.getLastName());
        filledRegistration.setEmail(registrationDTO.getEmail());

        when(registrationMapper.toEntity(registrationDTO)).thenReturn(filledRegistration);
        when(registrationRepository.getByToken(eq("asd"))).thenReturn(registration);

        registrationService.fillRegistrationInfo(registrationDTO);

        verify(registrationRepository).save(ArgumentMatchers.eq(filledRegistration));
        verify(registrationValidator).validate(filledRegistration);
        verify(registrationRepository).getByToken(eq("asd"));
    }

    @Test
    @DisplayName("Fill in invalid registration info")
    void fillInRegistration_throwsException_ifInfoIsInvalid() {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setToken("asd");
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("john@doe");

        Registration registration = new Registration("asd");

        Registration filledRegistration = new Registration();
        filledRegistration.setToken(registrationDTO.getToken());
        filledRegistration.setFirstName(registrationDTO.getFirstName());
        filledRegistration.setLastName(registrationDTO.getLastName());
        filledRegistration.setEmail(registrationDTO.getEmail());

        when(registrationMapper.toEntity(registrationDTO)).thenReturn(filledRegistration);

        when(registrationRepository.getByToken(eq("asd"))).thenReturn(registration);

        doThrow(IllegalArgumentException.class).when(registrationValidator).validate(any(Registration.class));
        try {
            registrationService.fillRegistrationInfo(registrationDTO);
            assert false;
        } catch (IllegalArgumentException ex) {
            verify(registrationRepository, never()).save(any(Registration.class));
            verify(registrationRepository).getByToken(eq("asd"));
        }
    }

    @Test
    @DisplayName("Complete registration process")
    void completeRegistration() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken("asd");
        Token token = new Token(tokenDTO.getToken());

        when(tokenMapper.toEntity(any(TokenDTO.class))).thenReturn(token);

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setToken(tokenDTO.getToken());
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setEmail("john@doe.com");

        Registration filledRegistration = new Registration();
        filledRegistration.setToken(registrationDTO.getToken());
        filledRegistration.setFirstName(registrationDTO.getFirstName());
        filledRegistration.setLastName(registrationDTO.getLastName());
        filledRegistration.setEmail(registrationDTO.getEmail());

        UserDTO userDTO = new UserDTO();

        when(registrationRepository.getByToken(tokenDTO.getToken())).thenReturn(filledRegistration);
        when(registrationMapper.toDTO(filledRegistration)).thenReturn(registrationDTO);
        when(userService.createUser(registrationDTO)).thenReturn(userDTO);

        UserDTO receivedUserDTO = registrationService.completeRegistration(tokenDTO);

        assertThat(userDTO).isEqualTo(receivedUserDTO);

        verify(userService).createUser(registrationDTO);
    }

    @Test
    @DisplayName("Complete unfilled registration")
    void completeRegistration_throwsException_ifRegistrationNotComplete() {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken("asd");
        Token token = new Token(tokenDTO.getToken());

        when(tokenMapper.toEntity(tokenDTO)).thenReturn(token);

        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setToken(tokenDTO.getToken());

        Registration emptyRegistration = new Registration();
        emptyRegistration.setToken(registrationDTO.getToken());

        when(registrationRepository.getByToken(tokenDTO.getToken())).thenReturn(emptyRegistration);
        when(registrationMapper.toDTO(emptyRegistration)).thenReturn(registrationDTO);

        doThrow(IllegalArgumentException.class).when(registrationValidator).validate(any(Registration.class));

        try {
            registrationService.completeRegistration(tokenDTO);
            assert false;
        } catch (IllegalArgumentException e) {
            verify(registrationValidator).validate(any(Registration.class));
            verify(userService, never()).createUser(registrationDTO);
        }
    }

    @TestConfiguration
    static class RegistrationServiceTestContextConfiguration {

        @Autowired
        private RegistrationRepository registrationRepository;

        @Autowired
        private UserServiceInterface userService;

        @Autowired
        private Validator<Token> tokenValidator;

        @Autowired
        private Validator<Registration> registrationValidator;

        @Autowired
        private Mapper<Token, TokenDTO> tokenMapper;

        @Autowired
        private Mapper<Registration, RegistrationDTO> registrationMapper;

        @Bean
        public RegistrationService registrationService() {
            return new RegistrationServiceImpl(registrationRepository, tokenValidator, registrationValidator, userService, tokenMapper, registrationMapper);
        }
    }
}