package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;

import java.util.Collection;

public interface RegistrationQueryService {
    Registration getRegistrationById(RegistrationId registrationId);

    Collection<Registration> getAllRegistrations();
}
