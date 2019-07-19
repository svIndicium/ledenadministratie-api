package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.user.dto.NewUserDTO;
import hu.indicium.dev.lit.user.exceptions.UserNotFoundException;
import hu.indicium.dev.lit.userdata.UserData;
import hu.indicium.dev.lit.userdata.UserDataServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public User createUser(NewUserDTO userDTO) {
        User user = new User(userDTO.getId(), (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        user = userRepository.save(user);
        UserData userData = userDataService.saveUserData(user, userDTO);
        user.setUserData(userData);
        return user;
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
