package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;

import java.util.List;

public interface AuthService {
    AuthUserDTO getAuthUser();

    String createAuthUser(AuthUserDTO authUser);

    String requestPasswordResetLink(String auth0UserId);

    void assignRolesToUser(String auth0UserId, List<String> roles);
}
