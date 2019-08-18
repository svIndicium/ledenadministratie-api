package hu.indicium.dev.lit.register;

import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.register.dto.TokenDTO;
import hu.indicium.dev.lit.user.UserServiceInterface;
import hu.indicium.dev.lit.user.dto.UserDTO;
import hu.indicium.dev.lit.util.Mapper;
import hu.indicium.dev.lit.util.Validator;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    private final Validator<Token> tokenValidator;

    private final Validator<Registration> registrationValidator;

    private final RegistrationRepository registrationRepository;

    private final UserServiceInterface userService;

    private final Mapper<Token, TokenDTO> tokenMapper;

    private final Mapper<Registration, RegistrationDTO> registrationMapper;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, Validator<Token> tokenValidator, Validator<Registration> registrationValidator, UserServiceInterface userService, Mapper<Token, TokenDTO> tokenMapper, Mapper<Registration, RegistrationDTO> registrationMapper) {
        this.registrationRepository = registrationRepository;
        this.tokenValidator = tokenValidator;
        this.registrationValidator = registrationValidator;
        this.userService = userService;
        this.tokenMapper = tokenMapper;
        this.registrationMapper = registrationMapper;
    }

    @Override
    public void startRegistration(TokenDTO tokenDTO) {
        Token token = tokenMapper.toEntity(tokenDTO);
        tokenValidator.validate(token);
        Registration registration = new Registration(token.getJwtToken());
        registrationRepository.save(registration);
    }

    @Override
    public void fillRegistrationInfo(RegistrationDTO registrationDTO) {
        Registration registration = registrationRepository.getByToken(registrationDTO.getToken());
        registration.setFirstName(registrationDTO.getFirstName());
        registration.setLastName(registrationDTO.getLastName());
        registration.setEmail(registrationDTO.getEmail());
        registrationValidator.validate(registration);
        registrationRepository.save(registration);
    }

    @Override
    public UserDTO completeRegistration(TokenDTO tokenDTO) {
        Token token = tokenMapper.toEntity(tokenDTO);
        Registration registration = registrationRepository.getByToken(token.getJwtToken());
        registrationValidator.validate(registration);
        RegistrationDTO registrationDTO = registrationMapper.toDTO(registration);
        return userService.createUser(registrationDTO);
    }
}
