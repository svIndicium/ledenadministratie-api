package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.studytype.StudyTypeMapper;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final Validator<User> userValidator;

    public UserServiceImpl(UserRepository userRepository, Validator<User> userValidator) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user = this.saveUser(user);
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO editUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        user.setFirstName(userDTO.getFirstName());
        user.setMiddleName(userDTO.getMiddleName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setStudyType(StudyTypeMapper.toEntity(userDTO.getStudyType()));
        user = this.saveUser(user);
        return UserMapper.toDTO(user);
    }

    private User saveUser(User user) {
        userValidator.validate(user);
        return userRepository.save(user);
    }
}
