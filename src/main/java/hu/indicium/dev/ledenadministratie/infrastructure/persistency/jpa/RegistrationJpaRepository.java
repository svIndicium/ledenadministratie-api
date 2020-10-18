package hu.indicium.dev.ledenadministratie.infrastructure.persistency.jpa;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface RegistrationJpaRepository extends JpaRepository<Registration, UUID> {
    boolean existsByRegistrationId(RegistrationId registrationId);

    Collection<Registration> getAll();
}
