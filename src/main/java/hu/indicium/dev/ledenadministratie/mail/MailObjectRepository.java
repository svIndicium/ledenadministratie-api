package hu.indicium.dev.ledenadministratie.mail;

import java.util.Optional;

public interface MailObjectRepository {
    int countByVerificationToken(String token);

    Optional<MailObject> findByVerificationToken(String token);

    boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress);

    MailObject save(MailObject mailObject);
}
