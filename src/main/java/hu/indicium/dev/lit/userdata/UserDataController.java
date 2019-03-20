package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.userdata.dto.UpdateUserDataDTO;
import hu.indicium.dev.lit.userdata.dto.UserDataDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserDataController {
    private final UserDataServiceInterface userDataService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserDataController(UserDataServiceInterface userDataService, ModelMapper modelMapper) {
        this.userDataService = userDataService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/userdata/{userId}")
    public UserDataDTO getDataById(@PathVariable Long userId) {
        return convertToDTO(userDataService.getUserData(userId));
    }

    @PutMapping("/userdata/{userId}")
    public UserDataDTO updateData(@PathVariable Long userId, @RequestBody UpdateUserDataDTO userDataDTO) {
        UserData userData = convertToEntity(userDataDTO);
        userData.setId(userId);
        return convertToDTO(userDataService.updateUserData(userData));
    }

    private UserDataDTO convertToDTO(UserData userData) {
        return modelMapper.map(userData, UserDataDTO.class);
    }

    private UserData convertToEntity(UpdateUserDataDTO updateUserDataDTO) {
        return modelMapper.map(updateUserDataDTO, UserData.class);
    }
}
