package hu.indicium.dev.ledenadministratie.infrastructure.persistency;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationRepository;
import hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa.RegistrationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.UUID;

@Repository
public class RegistrationRepositoryImpl implements RegistrationRepository {

    private final RegistrationJpaRepository registrationRepository;

    public RegistrationRepositoryImpl(RegistrationJpaRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public RegistrationId nextIdentity() {
        UUID uuid = UUID.randomUUID();
        RegistrationId registrationId = RegistrationId.fromId(uuid);
        if (registrationRepository.existsByRegistrationId(registrationId)) {
            return nextIdentity();
        }
        return registrationId;
    }

    @Override
    public Registration getRegistrationById(RegistrationId registrationId) {
        return registrationRepository.findByRegistrationId(registrationId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Registration %s not found.", registrationId.getId().toString())));
    }

    @Override
    public Registration save(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public Collection<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
}
