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
import hu.indicium.dev.ledenadministratie.infrastructure.auth.Auth0User;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import lombok.AllArgsConstructor;
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
    public void reviewRegistration(ReviewRegistrationCommand reviewRegistrationCommand) {
        RegistrationId registrationId = RegistrationId.fromId(reviewRegistrationCommand.getRegistrationId());

        Registration registration = registrationRepository.getRegistrationById(registrationId);

        Auth0User auth0User = authService.getCurrentUser();

        switch (reviewRegistrationCommand.getReviewStatus()) {
            case DENIED:
                registration.deny(auth0User.getName(), reviewRegistrationCommand.getComment());
                break;
            case APPROVED:
                registration.approve(auth0User.getName());
                break;
            case PENDING:
            default:
                break;
        }

        registrationRepository.save(registration);
    }
}
