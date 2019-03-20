package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.user.dto.NewUserDTO;
import hu.indicium.dev.lit.userdata.UserDataServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository userRepository;

    private final UserDataServiceInterface userDataServiceInterface;

    @Autowired
    public UserService(UserRepository userRepository, UserDataServiceInterface userDataServiceInterface) {
        this.userRepository = userRepository;
        this.userDataServiceInterface = userDataServiceInterface;
    }

    @Override
    public User createUser(NewUserDTO userDTO) {
        User user = new User(userDTO.getId());
        user = userRepository.save(user);
        user.setUserData(userDataServiceInterface.createUserData(userDTO));
        return userRepository.save(user);
    }
}
