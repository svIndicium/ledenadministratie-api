package hu.indicium.dev.lit.register;

import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.register.dto.TokenDTO;
import hu.indicium.dev.lit.register.requests.FillRegistrationInfoRequest;
import hu.indicium.dev.lit.register.requests.StartRegistrationRequest;
import hu.indicium.dev.lit.register.requests.ValidateRegistrationRequest;
import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import hu.indicium.dev.lit.user.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/signup/start")
    public Response startRegistration(@RequestBody @Valid StartRegistrationRequest startRegistrationRequest) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(startRegistrationRequest.getToken());
        registrationService.startRegistration(tokenDTO);
        return new SuccessResponse(new HashMap<>());
    }

    @PostMapping("/signup")
    public Response fillRegistration(@RequestBody @Valid FillRegistrationInfoRequest fillRegistrationInfoRequest) {
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setToken(fillRegistrationInfoRequest.getToken());
        registrationDTO.setEmail(fillRegistrationInfoRequest.getEmail());
        registrationDTO.setFirstName(fillRegistrationInfoRequest.getFirstName());
        registrationDTO.setLastName(fillRegistrationInfoRequest.getLastName());
        registrationService.fillRegistrationInfo(registrationDTO);
        return new SuccessResponse(new HashMap<>());
    }

    @PostMapping("/signup/validate")
    public Response validateSignUp(@RequestBody @Valid ValidateRegistrationRequest validateRegistrationRequest) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(validateRegistrationRequest.getToken());
        UserDTO userDTO = registrationService.completeRegistration(tokenDTO);
        return new SuccessResponse(userDTO);
    }

}
