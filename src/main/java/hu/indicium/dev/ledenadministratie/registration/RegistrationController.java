package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.registration.requests.CreateRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
