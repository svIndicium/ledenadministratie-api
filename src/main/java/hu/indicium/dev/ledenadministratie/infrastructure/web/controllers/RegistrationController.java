package hu.indicium.dev.ledenadministratie.infrastructure.web.controllers;

import hu.indicium.dev.ledenadministratie.application.commands.NewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.application.commands.ReviewRegistrationCommand;
import hu.indicium.dev.ledenadministratie.application.query.RegistrationQueryService;
import hu.indicium.dev.ledenadministratie.application.service.RegistrationService;
import hu.indicium.dev.ledenadministratie.domain.model.user.ReviewStatus;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.Registration;
import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.MemberDTO;
import hu.indicium.dev.ledenadministratie.infrastructure.web.dto.RegistrationDTO;
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
    public Response<RegistrationDTO> createNewRegistration(@RequestBody NewRegistrationCommand newRegistrationCommand) {
        RegistrationId registrationId = registrationService.register(newRegistrationCommand);
        Registration registration = queryService.getRegistrationById(registrationId);
        RegistrationDTO registrationDTO = new RegistrationDTO(registration);
        return ResponseBuilder.created()
                .data(registrationDTO)
                .build();
    }

    @GetMapping("/registrations")
    @ResponseStatus(HttpStatus.OK)
    public Response<Collection<RegistrationDTO>> getAllRegistrations() {
        Collection<Registration> registrations = queryService.getAllRegistrations();
        Collection<RegistrationDTO> registrationDTOS = registrations.stream()
                .map(RegistrationDTO::new)
                .collect(Collectors.toSet());
        return ResponseBuilder.created()
                .data(registrationDTOS)
                .build();
    }

    @PostMapping("/registrations/{registrationId}/review")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<RegistrationDTO> reviewRegistration(@RequestBody ReviewRegistrationCommand reviewRegistrationCommand, @PathVariable UUID registrationId) {
        reviewRegistrationCommand.setRegistrationId(registrationId);
        registrationService.reviewRegistration(reviewRegistrationCommand);
        Registration registration = queryService.getRegistrationById(RegistrationId.fromId(registrationId));
        RegistrationDTO registrationDTO = new RegistrationDTO(registration);
        return ResponseBuilder.accepted()
                .data(registrationDTO)
                .build();
    }
}
