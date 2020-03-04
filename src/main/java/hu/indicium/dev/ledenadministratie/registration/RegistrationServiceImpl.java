package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.auth.AuthService;
import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.user.UserService;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final Mapper<Registration, RegistrationDTO> registrationMapper;

    private final UserService userService;

    private final Validator<Registration> registrationValidator;

    private final AuthService authService;

    private final RegistrationUserMapper registrationUserMapper;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, Mapper<Registration, RegistrationDTO> registrationMapper, UserService userService, Validator<Registration> registrationValidator, AuthService authService, RegistrationUserMapper registrationUserMapper) {
        this.registrationRepository = registrationRepository;
        this.registrationMapper = registrationMapper;
        this.userService = userService;
        this.registrationValidator = registrationValidator;
        this.authService = authService;
        this.registrationUserMapper = registrationUserMapper;
    }

    @Override
    public RegistrationDTO register(RegistrationDTO registration) {
        Registration registrationEntity = registrationMapper.toEntity(registration);
        registrationEntity.setFinalizedAt(null);
        registrationEntity.setFinalizedBy(null);
        registrationEntity.setApproved(false);
        registrationEntity.setComment(null);
        registrationEntity.setId(null);
        registrationEntity = saveRegistration(registrationEntity);
        return registrationMapper.toDTO(registrationEntity);
    }

    @Override
    @Transactional
    public RegistrationDTO finalizeRegistration(FinishRegistrationDTO finishRegistration) {
        Registration registration = getRegistrationById(finishRegistration.getRegistrationId());
        AuthUserDTO authUserDTO = authService.getAuthUser();
        registration.setFinalizedBy(authUserDTO.getName());
        registration.setApproved(finishRegistration.isApproved());
        registration.setComment(finishRegistration.getComment());
        registration.setFinalizedAt(new Date());
        registration = this.saveRegistration(registration);
        RegistrationDTO registrationDTO = registrationMapper.toDTO(registration);
        if (registration.isApproved()) {
            UserDTO userDTO = registrationUserMapper.toDTO(registrationDTO);
            userService.createUser(userDTO);
        }
        return registrationDTO;
    }

    @Override
    public List<RegistrationDTO> getRegistrations() {
        return registrationRepository.findAll()
                .stream()
                .map(registrationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistrationDTO> getRegistrationByFinalization(boolean finalized) {
        if (finalized) {
            return registrationRepository.findAllByApprovedIsTrueOrApprovedIsFalseAndCommentIsNotNull()
                    .stream()
                    .map(registrationMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            return registrationRepository.findAllByApprovedIsFalseAndCommentIsNull()
                    .stream()
                    .map(registrationMapper::toDTO)
                    .collect(Collectors.toList());
        }
    }

    public RegistrationDTO getRegistration(Long registrationId) {
        Registration registration = this.getRegistrationById(registrationId);
        return registrationMapper.toDTO(registration);
    }

    private Registration getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + id + " not found!"));
    }

    private Registration saveRegistration(Registration registration) {
        registrationValidator.validate(registration);
        return registrationRepository.save(registration);
    }
}
