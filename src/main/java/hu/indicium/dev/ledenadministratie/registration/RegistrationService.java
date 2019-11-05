package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;

public interface RegistrationService {
    RegistrationDTO register(RegistrationDTO registration);

    RegistrationDTO finalizeRegistration(FinishRegistrationDTO finishRegistration);
}
