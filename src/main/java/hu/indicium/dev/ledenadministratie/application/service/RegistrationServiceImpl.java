package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.NewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.application.commands.ReviewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeRepository;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationRepository;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final StudyTypeRepository studyTypeRepository;

    private final AuthService authService;

    @Override
    public RegistrationId register(NewRegistrationCommand newRegistration) {
        RegistrationId registrationId = registrationRepository.nextIdentity();

        StudyTypeId studyTypeId = StudyTypeId.fromId(newRegistration.getStudyTypeId());

        StudyType studyType = studyTypeRepository.getStudyTypeById(studyTypeId);

        Name name = new Name(newRegistration.getFirstName(), newRegistration.getMiddleName(), newRegistration.getLastName());

        MemberDetails memberDetails = new MemberDetails(name, newRegistration.getPhoneNumber(), newRegistration.getDateOfBirth(), studyType);

        MailAddress mailAddress = new MailAddress(newRegistration.getMailAddress(), newRegistration.isReceivingNewsletter());

        Registration registration = new Registration(registrationId, memberDetails, mailAddress);

        registrationRepository.save(registration);

        return registrationId;
    }

    @Override
    @PreAuthorize("hasPermission('review-registrations')")
    public void reviewRegistration(ReviewRegistrationCommand reviewRegistrationCommand) {
        RegistrationId registrationId = RegistrationId.fromId(reviewRegistrationCommand.getId());

        Registration registration = registrationRepository.getRegistrationById(registrationId);

        User user = authService.getCurrentUser();

        switch (reviewRegistrationCommand.getReviewStatus()) {
            case DENIED:
                registration.deny(user.getName(), reviewRegistrationCommand.getComment());
                break;
            case APPROVED:
                registration.approve(user.getName());
                break;
            case PENDING:
            default:
                break;
        }

        registrationRepository.save(registration);
    }
}
