package hu.indicium.dev.lit.userdata;

import hu.indicium.dev.lit.user.dto.NewUserDTO;

public interface UserDataServiceInterface {
    UserData createUserData(NewUserDTO userDTO);
}
