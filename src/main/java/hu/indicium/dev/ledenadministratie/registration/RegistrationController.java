package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import hu.indicium.dev.ledenadministratie.registration.requests.FinishRegistrationRequest;
import hu.indicium.dev.ledenadministratie.util.Response;
import hu.indicium.dev.ledenadministratie.util.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static hu.indicium.dev.ledenadministratie.util.BaseUrl.API_V1;

@RestController
@RequestMapping(API_V1 + "/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Response<List<RegistrationDTO>> getRegistrations(@RequestParam(name = "finalized", required = false, defaultValue = "") String getFinalizedRegistrations) {
        List<RegistrationDTO> registrations;
        if (getFinalizedRegistrations.equals("true")) {
            registrations = registrationService.getRegistrationByFinalization(true);
        } else if (getFinalizedRegistrations.equals("false")) {
            registrations = registrationService.getRegistrationByFinalization(false);
        } else {
            registrations = registrationService.getRegistrations();
        }
        return ResponseBuilder.ok()
                .data(registrations)
                .build();
    }

    @GetMapping("/{registrationId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<RegistrationDTO> getRegistration(@PathVariable Long registrationId) {
        return ResponseBuilder.ok()
                .data(registrationService.getRegistration(registrationId))
                .build();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<RegistrationDTO> register(@RequestBody @Valid CreateRegistrationRequest createRegistrationRequest) {
        RegistrationDTO registrationDTO = RegistrationRequestMapper.toDTO(createRegistrationRequest);
        RegistrationDTO newRegistration = registrationService.register(registrationDTO);
        return ResponseBuilder.created()
                .data(newRegistration)
                .build();
    }

    @PostMapping("/{registrationId}/finalize")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response<RegistrationDTO> finalizeRegistration(@PathVariable Long registrationId, @RequestBody @Valid FinishRegistrationRequest finishRegistrationRequest) {
        FinishRegistrationDTO finishRegistrationDTO = new FinishRegistrationDTO(registrationId, finishRegistrationRequest.getComment(), finishRegistrationRequest.isApproved());
        return ResponseBuilder.accepted()
                .data(registrationService.finalizeRegistration(finishRegistrationDTO))
                .build();
    }
}
