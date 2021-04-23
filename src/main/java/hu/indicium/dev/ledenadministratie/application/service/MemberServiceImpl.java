package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.ImportMemberCommand;
import hu.indicium.dev.ledenadministratie.domain.model.payment.PaymentId;
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
import hu.indicium.dev.ledenadministratie.infrastructure.payment.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    private final PaymentService paymentService;

    @Override
    public MemberId registerMember(RegistrationId registrationId) {

        Registration registration = registrationRepository.getRegistrationById(registrationId);

        Member member = Member.fromRegistration(registration);

        MembershipId membershipId = membershipRepository.nextIdentity();

        Membership membership = new Membership(membershipId, new Date(120, Calendar.SEPTEMBER, 1), new Date(121, Calendar.AUGUST, 31), member);

        PaymentId paymentId = paymentService.createContributionPayment(member);

        membership.assignPayment(paymentId);

        member.addMembership(membership);

        memberRepository.save(member);

        return member.getMemberId();
    }

    @Override
    @Transactional
    @PreAuthorize("hasPermission('manage-members')")
    public MemberId importMember(ImportMemberCommand importMemberCommand) {

        StudyType studyType = studyTypeRepository.getStudyTypeById(StudyTypeId.fromId(importMemberCommand.getStudyTypeId()));

        Name name = new Name(importMemberCommand.getFirstName(), importMemberCommand.getMiddleName(), importMemberCommand.getLastName());

        MemberDetails memberDetails = new MemberDetails(name, importMemberCommand.getPhoneNumber(), importMemberCommand.getDateOfBirth(), studyType);

        MailAddress mailAddress = new MailAddress(importMemberCommand.getMailAddress(), importMemberCommand.isReceivingNewsletter());

        RegistrationId registrationId = authService.createAccountForUser(memberDetails, mailAddress);

        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        registration.approve("Joost Lekkerkerker");

        registration = registrationRepository.save(registration);

        Member member = Member.fromRegistration(registration);

        member = memberRepository.save(member);

        if (importMemberCommand.isOldMember()) {
            MembershipId membershipId = membershipRepository.nextIdentity();
            Membership membership = new Membership(membershipId, new Date(119, Calendar.SEPTEMBER, 1), new Date(120, Calendar.AUGUST, 31), member);
            member.addMembership(membership);
        }
        if (importMemberCommand.isNewMember()) {
            MembershipId membershipId = membershipRepository.nextIdentity();
            Membership membership = new Membership(membershipId, new Date(120, Calendar.SEPTEMBER, 1), new Date(121, Calendar.AUGUST, 31), member);
            member.addMembership(membership);
        }

        memberRepository.save(member);

        return member.getMemberId();
    }
}
