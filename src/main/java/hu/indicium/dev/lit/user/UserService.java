package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.register.mapper.RegistrationMapper;
import hu.indicium.dev.lit.user.dto.UserDTO;
import hu.indicium.dev.lit.user.exceptions.UserNotFoundException;
import hu.indicium.dev.lit.userdata.UserData;
import hu.indicium.dev.lit.userdata.UserDataServiceInterface;
import hu.indicium.dev.lit.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    private final UserDataServiceInterface userDataService;

    @Autowired
    public UserService(UserRepository userRepository, UserDataServiceInterface userDataService) {
        this.userRepository = userRepository;
        this.userDataService = userDataService;
    }

    @Override
    @Transactional
    public UserDTO createUser(RegistrationDTO registrationDTO) {
        Mapper<Registration, RegistrationDTO> registrationMapper = new RegistrationMapper();
        Registration registration = registrationMapper.toEntity(registrationDTO);
        User user = new User();
        user = userRepository.save(user);
        UserData userData = userDataService.saveUserData(user, registration);
        user.setUserData(userData);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getUserByAuthUserId(String authUserId) {
        return userRepository.findByAuthUserId(authUserId);
    }

}
