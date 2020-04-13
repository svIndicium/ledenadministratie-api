package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.mail.MailService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailEntryDTO;
import hu.indicium.dev.ledenadministratie.mail.dto.TransactionalMailDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.events.MailAddressVerified;
import hu.indicium.dev.ledenadministratie.user.events.UserCreated;
import hu.indicium.dev.ledenadministratie.util.Util;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, ApplicationListener<MailAddressVerified> {

    private final UserRepository userRepository;

    private final Validator<User> userValidator;

    private final ModelMapper modelMapper;

    private final MailService mailService;

    private final MailAddressRepository mailAddressRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public UserServiceImpl(UserRepository userRepository, Validator<User> userValidator, ModelMapper modelMapper, MailService mailService, MailAddressRepository mailAddressRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
        this.mailAddressRepository = mailAddressRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission('create:user')")
    public UserDTO createUser(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setMiddleName(registrationDTO.getMiddleName());
        user.setLastName(registrationDTO.getLastName());
        user.setDateOfBirth(registrationDTO.getDateOfBirth());
        user.setPhoneNumber(registrationDTO.getPhoneNumber());
        user.setStudyType(new StudyType(registrationDTO.getStudyTypeId()));
        MailAddress mailAddress = new MailAddress(registrationDTO.getMailAddress(), registrationDTO.getVerificationToken(), registrationDTO.getVerificationRequestedAt(), registrationDTO.getVerifiedAt(), registrationDTO.isToReceiveNewsletter());
        user.addMailAddress(mailAddress);
        user = this.saveUser(user);
        mailAddress.setUser(user);
        mailAddress.setId(0L);
        mailAddressRepository.save(mailAddress);
        applicationEventPublisher.publishEvent(new UserCreated(this, user.getId()));
        return UserMapper.map(user);
    }

    @Override
    @PreAuthorize("hasPermission('write:user')")
    public UserDTO updateUser(UserDTO userDTO) {
        User user = findUserById(userDTO.getId());
        modelMapper.map(userDTO, user);
        user = this.saveUser(user);
        return UserMapper.map(user);
    }

    @Override
    @PreAuthorize("hasPermission('read:user') || hasPermission('admin:user')")
    public UserDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return UserMapper.map(user);
    }

    @Override
    @PreAuthorize("hasPermission('admin:user')")
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = UserMapper.map(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    @PreAuthorize("hasPermission('write:user') || hasPermission('admin:user')")
    public MailAddressDTO requestNewMailVerification(Long userId, Long mailId) {
        User user = findUserById(userId);
        MailAddress mailAddress = getMailAddressByUserIdAndMailId(userId, mailId);
        if (mailAddress.getVerifiedAt() != null) {
            throw new IllegalStateException("Email address already verified");
        }
        mailAddress = sendVerificationMail(mailAddress, user);
        mailAddress = mailAddressRepository.save(mailAddress);
        return MailMapper.map(mailAddress);
    }

    @Override
    @PreAuthorize("hasPermission('read:user') || hasPermission('admin:user')")
    public List<MailAddressDTO> getMailAddressesByUserId(Long userId) {
        return mailAddressRepository.findAllByUserId(userId).stream()
                .map(MailMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasPermission('write:user')")
    public UserDTO addMailAddressToUser(Long userId, MailAddressDTO mailAddressDTO) {
        if (mailAddressRepository.existsByMailAddressAndVerifiedAtIsNotNull(mailAddressDTO.getAddress())) {
            throw new IllegalArgumentException("Email address already in use");
        }
        if (mailAddressRepository.existsByMailAddressAndUserId(mailAddressDTO.getAddress(), userId)) {
            throw new IllegalArgumentException("Email address already added");
        }
        User user = findUserById(userId);
        MailAddress mailAddress = new MailAddress(mailAddressDTO.getAddress(), mailAddressDTO.isReceivesNewsletter());
        mailAddress = sendVerificationMail(mailAddress, user);
        user.addMailAddress(mailAddress);
        userRepository.save(user);
        return UserMapper.map(user);
    }

    private MailAddress sendVerificationMail(MailAddress mailAddress, User user) {
        TransactionalMailDTO transactionalMailDTO = new TransactionalMailDTO(user.getFirstName(), Util.getFullLastName(user.getMiddleName(), user.getLastName()));
        return (MailAddress) mailService.sendVerificationMail(mailAddress, transactionalMailDTO);
    }

    private User saveUser(User user) {
        userValidator.validate(user);
        return userRepository.save(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found!"));
    }

    private MailAddress getMailAddressByUserIdAndMailId(Long userId, Long mailId) {
        return mailAddressRepository.findByUserIdAndId(userId, mailId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Mail address with id %d not found for user with id %d", mailId, userId)));
    }

    @Override
    public void onApplicationEvent(MailAddressVerified event) {
        MailAddress mailAddress = getMailAddressByUserIdAndMailId(event.getUserId(), event.getMailAddressId());
        User user = mailAddress.getUser();
        MailEntryDTO mailEntryDTO = new MailEntryDTO(user.getFirstName(), user.getFullLastName(), mailAddress.getMailAddress());
        mailService.addMailAddressToMailingList(mailEntryDTO);
        if (mailAddress.receivesNewsletter()) {
            mailService.addMailAddressToNewsletter(mailEntryDTO);
        }
    }
}
