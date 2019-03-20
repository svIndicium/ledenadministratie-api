package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import hu.indicium.dev.lit.user.dto.NewUserDTO;
import hu.indicium.dev.lit.userdata.UserData;
import hu.indicium.dev.lit.userdata.dto.UserDataDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserServiceInterface userService;

    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserServiceInterface userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/users")
    @PreAuthorize("#oauth2.hasScope('lit:create')")
    public Response saveUser(@RequestBody NewUserDTO userDTO) {
        return new SuccessResponse(convertToDTO(userService.createUser(userDTO).getUserData()));
    }

    private UserDataDTO convertToDTO(UserData userData) {
        return modelMapper.map(userData, UserDataDTO.class);
    }
}
