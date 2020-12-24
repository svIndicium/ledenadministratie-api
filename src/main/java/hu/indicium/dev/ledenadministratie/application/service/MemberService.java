package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.ImportMemberCommand;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;

public interface MemberService {
    MemberId registerMember(RegistrationId registrationId);

    @Deprecated
    MemberId importMember(ImportMemberCommand importMemberCommand);
}
