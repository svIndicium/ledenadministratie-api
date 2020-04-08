package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.auth.AuthService;
import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.mail.MailService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.events.NewRegistrationAdded;
import hu.indicium.dev.ledenadministratie.registration.events.RegistrationFinalized;
import hu.indicium.dev.ledenadministratie.user.UserService;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import hu.indicium.dev.ledenadministratie.util.Util;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;


    private final UserService userService;

    private final AuthService authService;

    private final MailService mailService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Validator<Registration> registrationValidator;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository, UserService userService, AuthService authService, MailService mailService, ApplicationEventPublisher applicationEventPublisher, Validator<Registration> registrationValidator) {
        this.registrationRepository = registrationRepository;
        this.userService = userService;
        this.authService = authService;
        this.mailService = mailService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.registrationValidator = registrationValidator;
    }

    @Override
    @Transactional
    public RegistrationDTO register(RegistrationDTO registrationDTO) {
        Registration registration = RegistrationMapper.map(registrationDTO);
        registration.setFinalizedAt(null);
        registration.setFinalizedBy(null);
        registration.setApproved(false);
        registration.setComment(null);
        registration.setId(null);
        MailVerificationDTO mailVerificationDTO = new MailVerificationDTO(registration.getFirstName(), Util.getFullLastName(registration.getMiddleName(), registration.getLastName()));
        mailService.sendVerificationMail(registration, mailVerificationDTO);
        registration = saveRegistration(registration);
        applicationEventPublisher.publishEvent(new NewRegistrationAdded(this, registration.getId()));
        return RegistrationMapper.map(registration);
    }

    @Override
    @Transactional
    public RegistrationDTO finalizeRegistration(FinishRegistrationDTO finishRegistration) {
        Registration registration = getRegistrationById(finishRegistration.getRegistrationId());
        if (registration.getVerifiedAt() == null) {
            throw new IllegalStateException("Emailaddress must be confirmed before finalizing");
        }
        AuthUserDTO authUserDTO = authService.getAuthUser();
        registration.setFinalizedBy(authUserDTO.getName());
        registration.setApproved(finishRegistration.isApproved());
        registration.setComment(finishRegistration.getComment());
        registration.setFinalizedAt(new Date());
        registration = this.saveRegistration(registration);
        RegistrationDTO registrationDTO = RegistrationMapper.map(registration);
        applicationEventPublisher.publishEvent(new RegistrationFinalized(this, registration.getId()));
        if (registration.isApproved()) {
            userService.createUser(registrationDTO);
        }
        return registrationDTO;
    }

    @Override
    public List<RegistrationDTO> getRegistrations() {
        return registrationRepository.findAll()
                .stream()
                .map(RegistrationMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegistrationDTO> getRegistrationByFinalization(boolean finalized) {
        if (finalized) {
            return registrationRepository.findAllByApprovedIsTrueOrApprovedIsFalseAndCommentIsNotNull()
                    .stream()
                    .map(RegistrationMapper::map)
                    .collect(Collectors.toList());
        } else {
            return registrationRepository.findAllByApprovedIsFalseAndCommentIsNull()
                    .stream()
                    .map(RegistrationMapper::map)
                    .collect(Collectors.toList());
        }
    }

    public RegistrationDTO getRegistration(Long registrationId) {
        Registration registration = this.getRegistrationById(registrationId);
        return RegistrationMapper.map(registration);
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
