package hu.indicium.dev.lit.register;

import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.register.dto.TokenDTO;
import hu.indicium.dev.lit.user.dto.UserDTO;

public interface RegistrationService {
    void startRegistration(TokenDTO tokenDTO);

    void fillRegistrationInfo(RegistrationDTO registrationDTO);

    UserDTO completeRegistration(TokenDTO tokenDTO);
}
