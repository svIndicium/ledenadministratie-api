package hu.indicium.dev.lit.register;

import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.register.dto.TokenDTO;
import hu.indicium.dev.lit.register.mapper.RegistrationMapper;
import hu.indicium.dev.lit.register.mapper.TokenMapper;
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

    public RegistrationServiceImpl(Validator<Token> tokenValidator, RegistrationRepository registrationRepository, Validator<Registration> registrationValidator, UserServiceInterface userService) {
        this.tokenValidator = tokenValidator;
        this.registrationRepository = registrationRepository;
        this.registrationValidator = registrationValidator;
        this.userService = userService;
    }

    @Override
    public void startRegistration(TokenDTO tokenDTO) {
        Mapper<Token, TokenDTO> tokenMapper = new TokenMapper();
        Token token = tokenMapper.toEntity(tokenDTO);
        tokenValidator.validate(token);
        Registration registration = new Registration(token.getJwtToken());
        registrationRepository.save(registration);
    }

    @Override
    public void fillRegistrationInfo(RegistrationDTO registrationDTO) {
        Mapper<Registration, RegistrationDTO> registrationMapper = new RegistrationMapper();
        Registration registration = registrationMapper.toEntity(registrationDTO);
        registrationValidator.validate(registration);
        registrationRepository.save(registration);
    }

    @Override
    public UserDTO completeRegistration(TokenDTO tokenDTO) {
        Mapper<Token, TokenDTO> tokenMapper = new TokenMapper();
        Mapper<Registration, RegistrationDTO> registrationMapper = new RegistrationMapper();
        Token token = tokenMapper.toEntity(tokenDTO);
        Registration registration = registrationRepository.getByToken(token.getJwtToken());
        RegistrationDTO registrationDTO = registrationMapper.toDTO(registration);
        return userService.createUser(registrationDTO);
    }
}
