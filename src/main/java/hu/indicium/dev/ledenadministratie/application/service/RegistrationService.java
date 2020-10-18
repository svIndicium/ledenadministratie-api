package hu.indicium.dev.ledenadministratie.application.service;

import hu.indicium.dev.ledenadministratie.application.commands.NewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;

public interface RegistrationService {
    RegistrationId register(NewRegistrationCommand newRegistrationCommand);
}
