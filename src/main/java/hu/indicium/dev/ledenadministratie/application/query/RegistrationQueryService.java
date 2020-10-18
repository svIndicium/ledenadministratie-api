package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;

public interface RegistrationQueryService {
    Registration getRegistrationById(RegistrationId registrationId);
}
