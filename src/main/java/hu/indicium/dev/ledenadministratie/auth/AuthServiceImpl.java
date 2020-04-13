package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.auth.requests.CreateUserRequest;
import hu.indicium.dev.ledenadministratie.auth.requests.RequestPasswordChangeTicketRequest;
import hu.indicium.dev.ledenadministratie.auth.responses.*;
import hu.indicium.dev.ledenadministratie.util.Util;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class AuthServiceImpl implements AuthService {

    private final RestTemplate restTemplate;

    private final AuthSettings authSettings;

    private AuthManagementToken authManagementToken;

    public AuthServiceImpl(RestTemplate restTemplate, AuthSettings authSettings) {
        this.restTemplate = restTemplate;
        this.authSettings = authSettings;
    }

    @Override
    public AuthUserDTO getAuthUser() {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBearerAuth(token);
        HttpEntity<UserInfoResponse> userInfoRequestHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<UserInfoResponse> userInfoRequestResponseEntity = restTemplate.exchange(authSettings.getIssuer() + "userinfo", HttpMethod.GET, userInfoRequestHttpEntity, UserInfoResponse.class);
        UserInfoResponse userInfoResponse = userInfoRequestResponseEntity.getBody();
        if (userInfoResponse == null) {
            throw new EntityNotFoundException("User could not identify itself");
        }
        AuthUser authUser = new AuthUser(userInfoResponse);
        return AuthUserMapper.map(authUser);
    }

    @Override
    public String createAuthUser(AuthUserDTO authUser) {
        String oldUserId = checkIfEmailAddressAlreadyExists(authUser.getEmail());
        if (oldUserId != null) {
            return oldUserId;
        }

        CreateUserRequest createUserRequest = new CreateUserRequest(authUser.getEmail(), authUser.getGivenName(), authUser.getFamilyName(), Util.generateTemporaryPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBearerAuth(getToken());

        HttpEntity<CreateUserRequest> requestEntity = new HttpEntity<>(createUserRequest, httpHeaders);

        ResponseEntity<CreateUserResponse> response = restTemplate.exchange(authSettings.getIssuer() + "api/v2/users", HttpMethod.POST, requestEntity, CreateUserResponse.class);
        assert response.getBody() != null;
        return response.getBody().getUserId();
    }

    @Override
    public String requestPasswordResetLink(String userId) {
        RequestPasswordChangeTicketRequest requestPasswordChangeTicketRequest = new RequestPasswordChangeTicketRequest(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBearerAuth(getToken());
        HttpEntity<RequestPasswordChangeTicketRequest> httpEntity = new HttpEntity<>(requestPasswordChangeTicketRequest, httpHeaders);
        ResponseEntity<RequestPasswordChangeTicketResponse> response = restTemplate.exchange(authSettings.getIssuer() + "api/v2/tickets/password-change", HttpMethod.POST, httpEntity, RequestPasswordChangeTicketResponse.class);
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() != null;
        return response.getBody().getTicket();
    }

    private String checkIfEmailAddressAlreadyExists(String email) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        httpHeaders.setBearerAuth(getToken());
        ResponseEntity<UserIdResponse[]> response = restTemplate.exchange(authSettings.getIssuer() + "api/v2/users-by-email?email=" + email + "&fields=user_id", HttpMethod.GET, new HttpEntity<>(httpHeaders), UserIdResponse[].class);
        assert response.getBody() != null;
        List<UserIdResponse> list = Arrays.asList(response.getBody());
        if (list.size() != 0) {
            return list.get(0).getUserId();
        }
        return null;
    }

    private String getToken() {
        if (authManagementToken == null || new Date().after(authManagementToken.getExpiresAt())) {
            requestNewToken();
        }
        return this.authManagementToken.getAccessToken();
    }

    private void requestNewToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", authSettings.getClientId());
        body.add("client_secret", authSettings.getClientSecret());
        body.add("audience", authSettings.getIssuer() + "api/v2/");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<GetTokenResponse> response = restTemplate.exchange(authSettings.getIssuer() + "oauth/token", HttpMethod.POST, entity, GetTokenResponse.class);

        GetTokenResponse getTokenResponse = response.getBody();
        assert getTokenResponse != null;
        assert getTokenResponse.getAccessToken() != null;
        assert getTokenResponse.getScope() != null;
        assert getTokenResponse.getTokenType() != null;
        this.authManagementToken = new AuthManagementToken();
        this.authManagementToken.setAccessToken(getTokenResponse.getAccessToken());
        this.authManagementToken.setExpiresIn(getTokenResponse.getExpiresIn());
        this.authManagementToken.setScopes(Arrays.asList(getTokenResponse.getScope().split(" ")));
        this.authManagementToken.setTokenType(getTokenResponse.getTokenType());
        this.authManagementToken.setAcquiredAt(new Date());
    }
}
