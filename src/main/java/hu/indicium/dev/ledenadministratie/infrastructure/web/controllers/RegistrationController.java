package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.commands.NewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.application.commands.ReviewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.application.query.RegistrationQueryService;
import hu.indicium.dev.ledenadministratie.application.service.RegistrationService;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.RegistrationDto;
import hu.indicium.dev.ledenadministratie.util.BaseUrl;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping(BaseUrl.API_V1)
public class RegistrationController {
    private final RegistrationService registrationService;

    private final RegistrationQueryService queryService;

    private final AuthService authService;

    @PostMapping("/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    public Response<RegistrationDto> createNewRegistration(@RequestBody NewRegistrationCommand newRegistrationCommand) {
        RegistrationId registrationId = registrationService.register(newRegistrationCommand);
        Registration registration = queryService.getRegistrationById(registrationId);
        RegistrationDto registrationDTO = new RegistrationDto(registration);
        return ResponseBuilder.created()
                .data(registrationDTO)
                .build();
    }

    @GetMapping("/registrations")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<RegistrationDto>> getAllRegistrations() {
        Collection<Registration> registrations = queryService.getAllRegistrations();
        Collection<RegistrationDto> registrationDtos = registrations.stream()
                .map(RegistrationDto::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.created()
                .data(registrationDtos)
                .build();
    }

    @PostMapping("/registrations/{registrationId}/review")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<RegistrationDto> reviewRegistration(@RequestBody ReviewRegistrationCommand reviewRegistrationCommand, @PathVariable UUID registrationId) {
        reviewRegistrationCommand.setId(registrationId);
        registrationService.reviewRegistration(reviewRegistrationCommand);
        Registration registration = queryService.getRegistrationById(RegistrationId.fromId(registrationId));
        RegistrationDto registrationDTO = new RegistrationDto(registration);
        return ResponseBuilder.accepted()
                .data(registrationDTO)
                .build();
    }
}
