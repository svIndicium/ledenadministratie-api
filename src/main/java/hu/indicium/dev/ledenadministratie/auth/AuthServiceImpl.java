package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.auth.requests.UserInfoRequest;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;


@Service
public class AuthServiceImpl implements AuthService {

    @Value("${auth0.issuer}")
    private String issuer;

    private final RestTemplate restTemplate;

    private final Mapper<AuthUser, AuthUserDTO> authUserMapper;

    public AuthServiceImpl(RestTemplate restTemplate, Mapper<AuthUser, AuthUserDTO> authUserMapper) {
        this.restTemplate = restTemplate;
        this.authUserMapper = authUserMapper;
    }

    @Override
    public AuthUserDTO getAuthUser() {
        String token = SecurityContextHolder.getContext().getAuthentication().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBearerAuth(token);
        HttpEntity<UserInfoRequest> userInfoRequestHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<UserInfoRequest> userInfoRequestResponseEntity = restTemplate.exchange(issuer + "/userinfo", HttpMethod.GET, userInfoRequestHttpEntity, UserInfoRequest.class);
        UserInfoRequest userInfoRequest = userInfoRequestResponseEntity.getBody();
        if (userInfoRequest == null) {
            throw new EntityNotFoundException("User could not identify itself");
        }
        AuthUser authUser = new AuthUser(userInfoRequest);
        return authUserMapper.toDTO(authUser);
    }
}
