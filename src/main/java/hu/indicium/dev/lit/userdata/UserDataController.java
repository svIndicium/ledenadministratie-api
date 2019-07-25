package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import hu.indicium.dev.lit.userdata.dto.UpdateUserDataDTO;
import hu.indicium.dev.lit.userdata.dto.UserDataDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasPermission('read:user_data')")
    public Response getDataById(@PathVariable Long userId) {
        return new SuccessResponse(convertToDTO(userDataService.getUserData(userId)));
    }

    @PutMapping("/userdata/{userId}")
    @PreAuthorize("hasPermission('edit:user_data')")
    public Response updateData(@PathVariable Long userId, @RequestBody UpdateUserDataDTO userDataDTO) {
        UserData userData = convertToEntity(userDataDTO);
        userData.setId(userId);
        return new SuccessResponse(convertToDTO(userDataService.updateUserData(userData)));
    }

    @DeleteMapping("/userdata/{userId}")
    @PreAuthorize("hasPermission('delete:user_data')")
    public Response deleteData(@PathVariable Long userId) {
        userDataService.deleteUserData(userId);
        return new SuccessResponse(null);
    }

    private UserDataDTO convertToDTO(UserData userData) {
        return modelMapper.map(userData, UserDataDTO.class);
    }

    private UserData convertToEntity(UpdateUserDataDTO updateUserDataDTO) {
        return modelMapper.map(updateUserDataDTO, UserData.class);
    }
}
