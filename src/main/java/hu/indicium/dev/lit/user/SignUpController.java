package hu.indicium.dev.lit.user;

import hu.indicium.dev.lit.response.Response;
import hu.indicium.dev.lit.response.SuccessResponse;
import hu.indicium.dev.lit.user.dto.RegisterSignUpDTO;
import hu.indicium.dev.lit.user.dto.SignUpDTO;
import hu.indicium.dev.lit.user.dto.ValidateSignUpDTO;
import hu.indicium.dev.lit.user.dto.ValidatedSignUpDTO;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class SignUpController {

    private final SignUpServiceInterface signUpService;

    private final ModelMapper modelMapper;

    public SignUpController(SignUpServiceInterface signUpService, ModelMapper modelMapper) {
        this.signUpService = signUpService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/signup/register")
    public Response registerSignUp(@RequestBody RegisterSignUpDTO signUpDTO) {
        signUpService.registerSignUp(signUpDTO.getToken());
        return new SuccessResponse(new HashMap<>());
    }

    @PostMapping("/signup")
    public Response createSignUp(@RequestBody SignUpDTO signUpDTO) {
        SignUp signUp = toEntity(signUpDTO);
        signUpService.signUp(signUp);
        return new SuccessResponse(new HashMap<>());
    }

    @PostMapping("/signup/validate")
    public Response validateSignUp(@RequestBody ValidateSignUpDTO validateSignUpDTO) {
        User user = signUpService.validate(validateSignUpDTO.getToken());
        ValidatedSignUpDTO dto = new ValidatedSignUpDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getUserData().getFirstName());
        dto.setLastName(user.getUserData().getLastName());
        dto.setEmail(user.getUserData().getEmail());
        return new SuccessResponse(dto);
    }

    private SignUp toEntity(SignUpDTO signUpDTO) {
        return modelMapper.map(signUpDTO, SignUp.class);
    }
}
