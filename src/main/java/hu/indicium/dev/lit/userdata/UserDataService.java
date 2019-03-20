package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.user.User;
import hu.indicium.dev.lit.user.dto.NewUserDTO;
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
    public UserData saveUserData(User user, NewUserDTO userDTO) {
        UserData userData = new UserDataBuilder(userDTO.getId())
                .setUser(user)
                .setFirstName(userDTO.getFirstName())
                .setLastName(userDTO.getLastName())
                .setGender(userDTO.getGender())
                .setDateOfBirth(userDTO.getDateOfBirth())
                .setStreet(userDTO.getStreet())
                .setHouseNumber(userDTO.getHouseNumber())
                .setZipCode(userDTO.getZipCode())
                .setCity(userDTO.getCity())
                .setCountry(userDTO.getCountry())
                .setEmail(userDTO.getEmail())
                .setPhoneNumber(userDTO.getPhoneNumber())
                .setStudentId(userDTO.getStudentId())
                .build();
        return validateAndSave(userData);
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

    private UserData validateAndSave(UserData userData) {
        return userDataRepository.save(userData);
    }
}
