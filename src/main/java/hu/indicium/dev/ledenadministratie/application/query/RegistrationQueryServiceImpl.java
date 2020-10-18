package hu.indicium.dev.ledenadministratie.application.query;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@AllArgsConstructor
@Service
public class RegistrationQueryServiceImpl implements RegistrationQueryService {

    private final RegistrationRepository registrationRepository;

    @Override
    public Registration getRegistrationById(RegistrationId registrationId) {
        return registrationRepository.getRegistrationById(registrationId);
    }

    @Override
    public Collection<Registration> getAllRegistrations() {
        return registrationRepository.getAllRegistrations();
    }
}
