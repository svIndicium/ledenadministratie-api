package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.FinishRegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import hu.indicium.dev.ledenadministratie.registration.requests.FinishRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    private final RegistrationRequestMapper registrationRequestMapper;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
        this.registrationRequestMapper = new RegistrationRequestMapper();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDTO register(@RequestBody @Valid CreateRegistrationRequest createRegistrationRequest) {
        RegistrationDTO registrationDTO = registrationRequestMapper.toDTO(createRegistrationRequest);
        return registrationService.register(registrationDTO);
    }

    @GetMapping
    public List<RegistrationDTO> getRegistrations(@RequestParam(name = "finalized", required = false, defaultValue = "") String getFinalizedRegistrations) {
        if (getFinalizedRegistrations.equals("true")) {
            return registrationService.getRegistrationByFinalization(true);
        } else if (getFinalizedRegistrations.equals("false")) {
            return registrationService.getRegistrationByFinalization(false);
        }
        return registrationService.getRegistrations();
    }

    @PostMapping("/{registrationId}/finalize")
    public RegistrationDTO finalizeRegistration(@PathVariable Long registrationId, @RequestBody FinishRegistrationRequest finishRegistrationRequest) {
        FinishRegistrationDTO finishRegistrationDTO = new FinishRegistrationDTO(registrationId, finishRegistrationRequest.getComment(), finishRegistrationRequest.isApproved());
        return registrationService.finalizeRegistration(finishRegistrationDTO);
    }
}
