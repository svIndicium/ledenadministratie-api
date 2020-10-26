package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;

import java.util.UUID;

public interface MemberService {
    MemberId registerMember(RegistrationId registrationId);
}
