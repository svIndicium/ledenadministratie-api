package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import java.util.Collection;

public interface RegistrationRepository {
    RegistrationId nextIdentity();

    Registration getRegistrationById(RegistrationId registrationId);

    Registration save(Registration registration);

    Collection<Registration> getAllRegistrations();
}
