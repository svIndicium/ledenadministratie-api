package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;

import java.util.UUID;

public interface AuthService {
    User getCurrentUser();

    RegistrationId createAccountForUser(MemberDetails memberDetails, MailAddress mailAddress);

    void requestPasswordReset(UUID authUuid);

    void requestAccountSetup(RegistrationId registrationId);

    void moveUserToGroup(UUID authUuid, String group);
}
