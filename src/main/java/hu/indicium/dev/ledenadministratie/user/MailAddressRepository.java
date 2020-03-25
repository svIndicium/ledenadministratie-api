package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailAddressRepository extends JpaRepository<MailAddress, Long> {
    boolean existsByMailAddressAndUserId(String address, Long userId);

    boolean existsByIdAndVerifiedAtIsNotNull(Long mailId);

    boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress);

    int countByVerificationToken(String verificationToken);

    Optional<MailObject> findByVerificationToken(String token);
}
