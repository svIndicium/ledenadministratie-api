package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.requests.UpdateUserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

//    @ApiOperation(value = "Create a new user", response = UserDTO.class)
//    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserDTO createUser(
//            @ApiParam(value = "User data to store", required = true) @RequestBody @Valid CreateUserRequest createUserRequest
//    ) {
//        UserDTO userDTO = userRequestMapper.toDTO(createUserRequest);
//        return userService.createUser(userDTO);
//    }

    @ApiOperation(value = "Update a user", response = UserDTO.class)
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(
            @ApiParam(value = "User data to update user") @RequestBody @Valid UpdateUserRequest updateUserRequest,
            @ApiParam(value = "Id of the to be updated user", required = true) @PathVariable("id") Long userId
    ) {
        UserDTO userDTO = userRequestMapper.toDTO(updateUserRequest);
        userDTO.setId(userId);
        return userService.updateUser(userDTO);
    }

    @ApiOperation(value = "Get user by id", response = UserDTO.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(
            @ApiParam(value = "Id of the requested user", required = true) @PathVariable("id") Long userId
    ) {
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "Get a list of all users", response = List.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/{userId}/mailaddresses", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<MailAddressDTO> getMailAddressesById(@PathVariable("userId") Long userId) {
        return userService.getMailAddressesByUserId(userId);
    }

    @GetMapping("/{userId}/mailaddresses/{mailAddressId}/requestverification")
    public MailAddressDTO requestNewEmailVerification(@PathVariable Long userId, @PathVariable Long mailAddressId) {
        System.out.println("lek");
        return userService.requestNewMailVerification(userId, mailAddressId);
    }
}
