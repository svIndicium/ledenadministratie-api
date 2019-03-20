package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.user.dto.NewUserDTO;
import hu.indicium.dev.lit.userdata.UserData;
import hu.indicium.dev.lit.userdata.UserDataServiceInterface;
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
    public User createUser(NewUserDTO userDTO) {
        User user = new User(userDTO.getId());
        user = userRepository.save(user);
        UserData userData = userDataService.saveUserData(user, userDTO);
        user.setUserData(userData);
        return user;
    }
}
