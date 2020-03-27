package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.hooks.CreationHook;
import hu.indicium.dev.ledenadministratie.hooks.UpdateHook;
import hu.indicium.dev.ledenadministratie.mail.MailService;
import hu.indicium.dev.ledenadministratie.mail.dto.MailVerificationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import hu.indicium.dev.ledenadministratie.util.Util;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator<User> userValidator;

    private final Mapper<User, UserDTO> userMapper;

    private final ModelMapper modelMapper;

    private final CreationHook<UserDTO> creationHook;

    private final UpdateHook<UserDTO> updateHook;

    private final MailService mailService;

    private final MailAddressRepository mailAddressRepository;

    private final StudyTypeService studyTypeService;

    public UserServiceImpl(UserRepository userRepository, Validator<User> userValidator, Mapper<User, UserDTO> userMapper, ModelMapper modelMapper, CreationHook<UserDTO> creationHook, UpdateHook<UserDTO> updateHook, MailService mailService, MailAddressRepository mailAddressRepository, StudyTypeService studyTypeService) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
        this.creationHook = creationHook;
        this.updateHook = updateHook;
        this.mailService = mailService;
        this.mailAddressRepository = mailAddressRepository;
        this.studyTypeService = studyTypeService;
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
        user.setStudyType(new StudyType(registrationDTO.getStudyType().getId()));
        MailAddress mailAddress = new MailAddress(registrationDTO.getMailAddress(), registrationDTO.getVerificationToken(), registrationDTO.getVerificationRequestedAt(), registrationDTO.getVerifiedAt(), registrationDTO.isToReceiveNewsletter());
        user.addMailAddress(mailAddress);
        user = this.saveUser(user);
        mailAddress.setUser(user);
        mailAddressRepository.save(mailAddress);
        UserDTO newUserDTO = userMapper.toDTO(user);
        creationHook.execute(null, newUserDTO);
        return userMapper.toDTO(user);
    }

    @Override
    @PreAuthorize("hasPermission('write:user')")
    public UserDTO updateUser(UserDTO userDTO) {
        User user = findUserById(userDTO.getId());
        UserDTO oldUser = userMapper.toDTO(user);
        modelMapper.map(userDTO, user);
        user = this.saveUser(user);
        UserDTO updatedUser = userMapper.toDTO(user);
        updateHook.execute(oldUser, updatedUser);
        return userMapper.toDTO(user);
    }

    @Override
    @PreAuthorize("hasPermission('read:user') || hasPermission('admin:user')")
    public UserDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return userMapper.toDTO(user);
    }

    @Override
    @PreAuthorize("hasPermission('admin:user')")
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = userMapper.toDTO(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    @PreAuthorize("hasPermission('write:user')")
    public void requestNewMailVerification(Long userId, Long mailId) {
        User user = findUserById(userId);
        MailAddress mailAddress = null;
        for (MailAddress address : user.getMailAddresses()) {
            if (address.getId().equals(mailId)) {
                mailAddress = address;
                break;
            }
        }
        if (mailAddress == null) {
            throw new EntityNotFoundException(String.format("Could not find mail address with id %d", mailId));
        }
        if (mailAddress.getVerifiedAt() != null) {
            throw new IllegalStateException("Email address already verified");
        }
        sendVerificationMail(mailAddress, user);
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
        sendVerificationMail(mailAddress, user);
        return userMapper.toDTO(user);
    }

    private void sendVerificationMail(MailAddress mailAddress, User user) {
        MailVerificationDTO mailVerificationDTO = new MailVerificationDTO(user.getFirstName(), Util.getFullLastName(user.getMiddleName(), user.getLastName()));
        mailAddress = (MailAddress) mailService.sendVerificationMail(mailAddress, mailVerificationDTO);
        user.addMailAddress(mailAddress);
        userRepository.save(user);
    }

    private User saveUser(User user) {
        userValidator.validate(user);
        return userRepository.save(user);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User " + userId + " not found!"));
    }
}
