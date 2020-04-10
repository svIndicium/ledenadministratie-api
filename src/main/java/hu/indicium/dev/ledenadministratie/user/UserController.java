package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.requests.UpdateUserRequest;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "User Endpoint", value = "Users")
public class UserController {

    private final UserService userService;

    private final UserRequestMapper userRequestMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        userRequestMapper = new UserRequestMapper();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<UserDTO>> getUsers() {
        return ResponseBuilder.ok()
                .data(userService.getUsers())
                .build();
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<UserDTO> getUser(@PathVariable Long userId) {
        return ResponseBuilder.ok()
                .data(userService.getUserById(userId))
                .build();
    }

    @PutMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<UserDTO> updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, @PathVariable Long userId) {
        UserDTO userDTO = userRequestMapper.toDTO(updateUserRequest);
        userDTO.setId(userId);
        return ResponseBuilder.accepted()
                .data(userService.updateUser(userDTO))
                .build();
    }

    @GetMapping(value = "/{userId}/mailaddresses", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<MailAddressDTO>> getMailAddressesById(@PathVariable Long userId) {
        return ResponseBuilder.ok()
                .data(userService.getMailAddressesByUserId(userId))
                .build();
    }

    @GetMapping(value = "/{userId}/mailaddresses/{mailAddressId}/requestverification", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<MailAddressDTO> requestNewEmailVerification(@PathVariable Long userId, @PathVariable Long mailAddressId) {
        return ResponseBuilder.accepted()
                .data(userService.requestNewMailVerification(userId, mailAddressId))
                .build();
    }
}
