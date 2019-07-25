package hu.indicium.dev.lit.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SignUpService implements SignUpServiceInterface {

    private final SignUpRepository signUpRepository;

    private JWTVerifier verifier;

    private final UserServiceInterface userService;

    public SignUpService(SignUpRepository signUpRepository, UserServiceInterface userService) {
        this.signUpRepository = signUpRepository;
        Algorithm algorithm = Algorithm.HMAC256("dsa");
        verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        this.userService = userService;
    }

    @Override
    public void registerSignUp(String token) {
        verifier.verify(token);
        SignUp signUp = new SignUp(token);
        signUpRepository.save(signUp);
    }

    @Override
    public void signUp(SignUp signUp) {
        SignUp currentSignUp = signUpRepository.getByToken(signUp.getToken());
        DecodedJWT jwt = verifier.verify(currentSignUp.getToken());
        Map<String, Claim> claims = jwt.getClaims();
        Claim email = claims.get("email");
        if (!email.asString().equals(signUp.getEmail())) {
            throw new RuntimeException("The email is not the same");
        }
        currentSignUp.setEmail(signUp.getEmail());
        currentSignUp.setFirstName(signUp.getFirstName());
        currentSignUp.setLastName(signUp.getLastName());
    }

    @Override
    public User validate(String token) {
        SignUp signUp = signUpRepository.getByToken(token);
        if (signUp.getEmail() != null && signUp.getFirstName() != null && signUp.getLastName() != null) {
            User user = userService.createUser(signUp);
            signUpRepository.delete(signUp);
            return user;
        } else {
            throw new RuntimeException("User not signed up yet");
        }
    }
}
