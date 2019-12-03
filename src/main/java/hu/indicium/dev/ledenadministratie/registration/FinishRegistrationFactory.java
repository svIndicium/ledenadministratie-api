package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;

public class FinishRegistrationFactory {
    public static FinishRegistrationDTO approveRegistration(Long registrationId) {
        return new FinishRegistrationDTO(registrationId, null, true);
    }

    public static FinishRegistrationDTO declineRegistration(Long registrationId, String comment) {
        return new FinishRegistrationDTO(registrationId, comment, false);
    }
}
