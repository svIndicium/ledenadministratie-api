package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    RegistrationDTO register(RegistrationDTO registration);

    RegistrationDTO finalizeRegistration(FinishRegistrationDTO finishRegistration);

    List<RegistrationDTO> getRegistrations();

    List<RegistrationDTO> getRegistrationByFinalization(boolean finalized);
}
