package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.requests.CreateUserRequest;
import hu.indicium.dev.ledenadministratie.user.requests.UpdateUserRequest;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@Api(tags = "User Endpoint", value = "Users")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    private final UserRequestMapper userRequestMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        userRequestMapper = new UserRequestMapper();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserDTO userDTO = userRequestMapper.toDTO(createUserRequest);
        return userService.createUser(userDTO);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@RequestBody @Valid UpdateUserRequest updateUserRequest, @PathVariable("id") Long userId) {
        UserDTO userDTO = userRequestMapper.toDTO(updateUserRequest);
        userDTO.setId(userId);
        return userService.updateUser(userDTO);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }
}
