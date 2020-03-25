package hu.indicium.dev.ledenadministratie.mail;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MailObjectComponent {
    private final List<MailObjectRepository> repositories;

    public MailObjectComponent(List<MailObjectRepository> repositories) {
        this.repositories = repositories;
    }

    public int countByVerificationToken(String token) {
        int count = 0;
        for (MailObjectRepository repository : repositories) {
            count += repository.countByVerificationToken(token);
        }
        return count;
    }

    public MailObjectRepository getRepositoryFromToken(String token) {
        List<MailObjectRepository> mailObjectRepositories = new ArrayList<>();
        for (MailObjectRepository repository : repositories) {
            Optional<MailObject> mailObject = repository.findByVerificationToken(token);
            if (mailObject.isPresent()) {
                mailObjectRepositories.add(repository);
            }
        }
        if (mailObjectRepositories.size() > 1) {
            throw new IllegalStateException("Two entries found");
        }
        if (mailObjectRepositories.size() == 0) {
            throw new IllegalStateException("Could not validate mail address");
        }
        return mailObjectRepositories.get(0);
    }

    public boolean isMailAddressAlreadyVerified(String mailAddress) {
        for (MailObjectRepository repository : repositories) {
            if (repository.existsByMailAddressAndVerifiedAtIsNotNull(mailAddress)) {
                return true;
            }
        }
        return false;
    }
}
