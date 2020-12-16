package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;

public interface AuthService {
    User getCurrentUser();

    MemberId createAccountForUser(MemberDetails memberDetails, MailAddress mailAddress);

    void requestPasswordReset(MemberId memberId);
}
