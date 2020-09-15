package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class RegistrationRequestMapper {

    public static RegistrationDTO toDTO(CreateRegistrationRequest createRegistrationRequest) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName(createRegistrationRequest.getFirstName());
        registrationDTO.setMiddleName(createRegistrationRequest.getMiddleName());
        registrationDTO.setLastName(createRegistrationRequest.getLastName());
        registrationDTO.setMailAddress(createRegistrationRequest.getMailAddress());
        registrationDTO.setPhoneNumber(createRegistrationRequest.getPhoneNumber());
        registrationDTO.setDateOfBirth(createRegistrationRequest.getDateOfBirth());
        registrationDTO.setStudyTypeId(createRegistrationRequest.getStudyTypeId());
        registrationDTO.setToReceiveNewsletter(createRegistrationRequest.isToReceiveNewsletter());
        return registrationDTO;
    }
}
