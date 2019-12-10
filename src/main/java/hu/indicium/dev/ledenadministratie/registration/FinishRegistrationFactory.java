package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.FinishRegistrationRequest;

public class FinishRegistrationFactory {
    public static FinishRegistrationDTO createFinishRegistrationDTO(Long registrationId, FinishRegistrationRequest finishRegistrationRequest) {
        return new FinishRegistrationDTO(registrationId, finishRegistrationRequest.getComment(), finishRegistrationRequest.isApproved());
    }
}
