package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.studytype.StudyTypeMapper;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator<User> userValidator;

    private final Mapper<User, UserDTO> userMapper;

    public UserServiceImpl(UserRepository userRepository, Validator<User> userValidator, Mapper<User, UserDTO> userMapper) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = this.saveUser(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User user = findUserById(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setStudyType(StudyTypeMapper.toEntity(userDTO.getStudyType()));
        user = this.saveUser(user);
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = findUserById(userId);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = userMapper.toDTO(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
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
