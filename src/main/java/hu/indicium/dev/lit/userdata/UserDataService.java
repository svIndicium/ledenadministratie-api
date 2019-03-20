package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.user.dto.NewUserDTO;
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
    public UserData createUserData(NewUserDTO userDTO) {
        UserData userData = new UserDataBuilder(userDTO.getId())
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
        return userDataRepository.save(userData);
    }
}
