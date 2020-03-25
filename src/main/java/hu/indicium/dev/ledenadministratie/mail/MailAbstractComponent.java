package hu.indicium.dev.ledenadministratie.mail;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MailAbstractComponent {
    private final List<MailAbstractRepository<? extends MailAbstract>> repositories;

    public MailAbstractComponent(List<MailAbstractRepository<? extends MailAbstract>> repositories) {
        this.repositories = repositories;
    }

    public int countByVerificationToken(String token) {
        int count = 0;
        for (MailAbstractRepository<? extends MailAbstract> repository : repositories) {
            count += repository.countByVerificationToken(token);
        }
        return count;
    }

    public MailAbstractRepository<? extends MailAbstract> getRepositoryFromToken(String token) {
        List<MailAbstractRepository<? extends MailAbstract>> mailAbstractRepositories = new ArrayList<>();
        for (MailAbstractRepository<? extends MailAbstract> repository : repositories) {
            Optional<? extends MailAbstract> mailAbstract = repository.findByVerificationToken(token);
            if (mailAbstract.isPresent()) {
                mailAbstractRepositories.add(repository);
            }
        }
        if (mailAbstractRepositories.size() > 1) {
            throw new IllegalStateException("Two entries found");
        }
        if (mailAbstractRepositories.size() == 0) {
            throw new IllegalStateException("Could not validate mail address");
        }
        return mailAbstractRepositories.get(0);
    }

    public boolean isMailAddressAlreadyVerified(String mailAddress) {
        for (MailAbstractRepository<? extends MailAbstract> repository : repositories) {
            if (repository.existsByMailAddressAndVerifiedAtIsNotNull(mailAddress)) {
                return true;
            }
        }
        return false;
    }
}
