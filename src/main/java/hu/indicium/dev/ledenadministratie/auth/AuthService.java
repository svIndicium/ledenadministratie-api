package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;

public interface AuthService {
    AuthUserDTO getAuthUser();
}
