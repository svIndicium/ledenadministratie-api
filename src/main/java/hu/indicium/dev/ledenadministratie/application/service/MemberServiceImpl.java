package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.ImportMemberCommand;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeRepository;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberRepository;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.Membership;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipId;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.membership.MembershipRepository;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationRepository;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final RegistrationRepository registrationRepository;

    private final AuthService authService;

    private final StudyTypeRepository studyTypeRepository;

    private final MembershipRepository membershipRepository;

    @Override
    public MemberId registerMember(RegistrationId registrationId) {

        Registration registration = registrationRepository.getRegistrationById(registrationId);

        MemberId memberId = authService.createAccountForUser(registration.getMemberDetails(), registration.getMailAddress());

        Member member = Member.fromRegistration(registration, memberId);

        memberRepository.save(member);

        return memberId;
    }

    @Override
    public MemberId importMember(ImportMemberCommand importMemberCommand) {
        RegistrationId registrationId = registrationRepository.nextIdentity();

        StudyType studyType = studyTypeRepository.getStudyTypeById(StudyTypeId.fromId(importMemberCommand.getStudyTypeId()));

        Name name = new Name(importMemberCommand.getFirstName(), importMemberCommand.getMiddleName(), importMemberCommand.getLastName());

        MemberDetails memberDetails = new MemberDetails(name, importMemberCommand.getPhoneNumber(), importMemberCommand.getDateOfBirth(), studyType);

        MailAddress mailAddress = new MailAddress(importMemberCommand.getMailAddress(), importMemberCommand.isReceivingNewsletter());

        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        registration.getMailAddress().verify();

        registration.approve("Joost Lekkerkerker");

        registration = registrationRepository.save(registration);

        MemberId memberId = authService.createAccountForUser(registration.getMemberDetails(), registration.getMailAddress());

        Member member = Member.fromRegistration(registration, memberId);

        member = memberRepository.save(member);

        if (importMemberCommand.isOldMember()) {
            MembershipId membershipId = membershipRepository.nextIdentity();
            Membership membership = new Membership(membershipId, new Date(2019, Calendar.SEPTEMBER, 1), new Date(2020, Calendar.AUGUST, 31), member);
            member.addMembership(membership);
        }
        if (importMemberCommand.isNewMember()) {
            MembershipId membershipId = membershipRepository.nextIdentity();
            Membership membership = new Membership(membershipId, new Date(2020, Calendar.SEPTEMBER, 1), new Date(2021, Calendar.AUGUST, 31), member);
            member.addMembership(membership);
        }

        memberRepository.save(member);

        return memberId;
    }
}
