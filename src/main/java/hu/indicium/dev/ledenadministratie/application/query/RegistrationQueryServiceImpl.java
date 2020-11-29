package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class RegistrationQueryServiceImpl implements RegistrationQueryService {

    private final RegistrationRepository registrationRepository;

    @Override
    @PreAuthorize("hasPermission('admin:registration') || hasPermission('read:registration')")
    public Registration getRegistrationById(RegistrationId registrationId) {
        return registrationRepository.getRegistrationById(registrationId);
    }

    @Override
    @PreAuthorize("hasPermission('admin:registration') || hasPermission('read:registration')")
    public Collection<Registration> getAllRegistrations() {
        return registrationRepository.getAllRegistrations();
    }
}
