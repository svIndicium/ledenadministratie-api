package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    UserDTO getUserById(Long userId);

    List<UserDTO> getUsers();

    UserDTO addMailAddressToUser(Long userId, String address, boolean receivesNewsletter);

    void requestNewMailVerification(Long userId, Long mailId);
}

