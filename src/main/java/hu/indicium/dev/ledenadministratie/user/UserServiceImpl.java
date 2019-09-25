package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.hooks.CreationHook;
import hu.indicium.dev.ledenadministratie.hooks.UpdateHook;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import hu.indicium.dev.ledenadministratie.util.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public UserServiceImpl(UserRepository userRepository, Validator<User> userValidator, Mapper<User, UserDTO> userMapper, ModelMapper modelMapper, CreationHook<UserDTO> creationHook, UpdateHook<UserDTO> updateHook) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.userMapper = userMapper;
        this.modelMapper = modelMapper;
        this.creationHook = creationHook;
        this.updateHook = updateHook;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        user = this.saveUser(user);
        UserDTO newUserDTO = userMapper.toDTO(user);
        creationHook.execute(null, newUserDTO);
        return userMapper.toDTO(user);
    }

    @Override
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
