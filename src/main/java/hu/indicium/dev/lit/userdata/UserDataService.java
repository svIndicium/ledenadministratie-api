package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.user.User;
import hu.indicium.dev.lit.userdata.exceptions.UserDataAlreadyStoredException;
import hu.indicium.dev.lit.userdata.exceptions.UserDataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataService implements UserDataServiceInterface {

    private final UserDataRepository userDataRepository;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    @Override
    public UserData saveUserData(User user, Registration registration) {
        if (this.exists(user.getId())) {
            throw new UserDataAlreadyStoredException();
        }
        UserData userData = new UserData();
        userData.setUser(user);
        userData.setFirstName(registration.getFirstName());
        userData.setLastName(registration.getLastName());
        userData.setEmail(registration.getEmail());
        return userDataRepository.save(userData);
    }

    @Override
    public UserData getUserData(Long userId) {
        return userDataRepository.findById(userId)
                .orElseThrow(UserDataNotFoundException::new);
    }

    @Override
    public UserData updateUserData(UserData newUserData) {
        UserData userData = this.getUserData(newUserData.getId());
        userData.setFirstName(newUserData.getFirstName());
        userData.setLastName(newUserData.getLastName());
        userData.setGender(newUserData.getGender());
        userData.setDateOfBirth(newUserData.getDateOfBirth());
        userData.setStreet(newUserData.getStreet());
        userData.setHouseNumber(newUserData.getHouseNumber());
        userData.setZipCode(newUserData.getZipCode());
        userData.setCity(newUserData.getCity());
        userData.setCountry(newUserData.getCountry());
        userData.setEmail(newUserData.getEmail());
        userData.setPhoneNumber(newUserData.getPhoneNumber());
        userData.setStudentId(newUserData.getStudentId());
        return validateAndSave(userData);
    }

    @Override
    public void deleteUserData(Long userId) {
        userDataRepository.deleteById(userId);
    }

    @Override
    public boolean exists(Long userId) {
        return userDataRepository.existsById(userId);
    }

    private UserData validateAndSave(UserData userData) {
        return userDataRepository.save(userData);
    }
}
