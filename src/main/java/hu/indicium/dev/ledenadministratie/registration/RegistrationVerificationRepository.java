package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import hu.indicium.dev.ledenadministratie.mail.MailObjectRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RegistrationVerificationRepository implements MailObjectRepository {

    private final RegistrationRepository registrationRepository;

    public RegistrationVerificationRepository(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public int countByVerificationToken(String token) {
        return registrationRepository.countByVerificationToken(token);
    }

    @Override
    public Optional<MailObject> findByVerificationToken(String token) {
        return registrationRepository.findByVerificationToken(token);
    }

    @Override
    public boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress) {
        return registrationRepository.existsByMailAddressAndVerifiedAtIsNotNull(mailAddress);
    }

    @Override
    public MailObject save(MailObject mailObject) {
        return registrationRepository.save((Registration) mailObject);
    }
}
