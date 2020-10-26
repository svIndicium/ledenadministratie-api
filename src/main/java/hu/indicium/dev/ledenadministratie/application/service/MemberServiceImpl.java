package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberRepository;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final RegistrationRepository registrationRepository;

    private final AuthService authService;

    @Override
    public MemberId registerMember(RegistrationId registrationId) {

        Registration registration = registrationRepository.getRegistrationById(registrationId);

        MemberId memberId = authService.createAccountForUser(registration.getMemberDetails(), registration.getMailAddress());

        Member member = Member.fromRegistration(registration, memberId);

        memberRepository.save(member);

        return memberId;
    }
}
