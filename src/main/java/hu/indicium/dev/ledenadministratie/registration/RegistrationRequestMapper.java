package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class RegistrationRequestMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public RegistrationDTO toDTO(CreateRegistrationRequest createRegistrationRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RegistrationDTO registrationDTO = modelMapper.map(createRegistrationRequest, RegistrationDTO.class);
        registrationDTO.setStudyTypeId(createRegistrationRequest.getStudyTypeId());
        return registrationDTO;
    }
}
