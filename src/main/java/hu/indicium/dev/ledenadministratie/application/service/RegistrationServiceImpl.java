package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.NewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeId;
import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyTypeRepository;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    private final StudyTypeRepository studyTypeRepository;

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
}
