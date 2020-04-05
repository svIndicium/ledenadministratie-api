package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.mail.MailObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MailAddressRepository extends JpaRepository<MailAddress, Long> {
    boolean existsByMailAddressAndUserId(String address, Long userId);

    boolean existsByIdAndVerifiedAtIsNotNull(Long mailId);

    boolean existsByMailAddressAndVerifiedAtIsNotNull(String mailAddress);

    int countByVerificationTokenAndVerificationTokenIsNotNull(String verificationToken);

    Optional<MailObject> findByVerificationTokenAndVerificationTokenIsNotNull(String token);

    List<MailAddress> findAllByUserId(Long userId);

    Long findUserIdByMailAddress(String mailAddress);
}
