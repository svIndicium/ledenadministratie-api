package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.auth.responses.UserInfoResponse;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthSettings;
import hu.indicium.dev.ledenadministratie.util.WithMockToken;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Auth service")
class AuthServiceImplTest {
    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private AuthSettings authSettings;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("Get authenticated user")
    @WithMockToken(scope = "create:user")
    void shouldReturnAuthUserDTO_whenGetAuthUser() {
        ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        when(authSettings.getApiAudience()).thenReturn("https://lit.indicium.hu");
        when(authSettings.getIssuer()).thenReturn("https://indicium.eu.auth0.com/");

        UserInfoResponse userInfoResponse = getUserInfoRequest();

        when(restTemplate.exchange(eq("https://indicium.eu.auth0.com/userinfo"), eq(HttpMethod.GET), httpEntityArgumentCaptor.capture(), eq(UserInfoResponse.class))).thenReturn(ResponseEntity.of(Optional.of(userInfoResponse)));

        AuthUser authUser = getAuthUser(userInfoResponse);

        AuthUserDTO authUserDTO = authService.getCurrentUser();

        HttpEntity httpEntity = httpEntityArgumentCaptor.getValue();
        List<String> authorizationHeaders = httpEntity.getHeaders().get("Authorization");
        if (authorizationHeaders != null) {
            String authorizationHeader = authorizationHeaders.get(0);
            assertThat(authorizationHeader).isEqualTo("Bearer " + SecurityContextHolder.getContext().getAuthentication().toString());
        } else {
            fail("No authorization header found!");
        }
    }

    @Test
    @DisplayName("Get authenticated user")
    @WithMockToken(scope = "create:user")
    void shouldThrowException_whenNoUserIsFound() {
        ArgumentCaptor<HttpEntity> httpEntityArgumentCaptor = ArgumentCaptor.forClass(HttpEntity.class);

        when(authSettings.getApiAudience()).thenReturn("https://lit.indicium.hu");
        when(authSettings.getIssuer()).thenReturn("https://indicium.eu.auth0.com/");

        UserInfoResponse userInfoResponse = getUserInfoRequest();

        when(restTemplate.exchange(eq("https://indicium.eu.auth0.com/userinfo"), eq(HttpMethod.GET), httpEntityArgumentCaptor.capture(), eq(UserInfoResponse.class))).thenReturn(ResponseEntity.status(401).body(null));
        try {
            AuthUserDTO authUserDTO = authService.getCurrentUser();
            fail("User should not be able to identify itself");
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    private UserInfoResponse getUserInfoRequest() {
        Map<String, Object> appMetadata = new HashMap<>();
        appMetadata.put("id", 1);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setSub("sub");
        userInfoResponse.setGivenName("John");
        userInfoResponse.setFamilyName("Doe");
        userInfoResponse.setNickname("johndoe123");
        userInfoResponse.setName("John Doe");
        userInfoResponse.setPictureUrl("");
        userInfoResponse.setLocale("nl");
        userInfoResponse.setUpdatedAt(new Date());
        userInfoResponse.setEmail("john@doe.com");
        userInfoResponse.setEmailVerified(true);
        userInfoResponse.setAppMetadata(appMetadata);
        return userInfoResponse;
    }

    private AuthUser getAuthUser(UserInfoResponse userInfoResponse) {
        AuthUser authUser = new AuthUser();
        authUser.setSub(userInfoResponse.getSub());
        authUser.setGivenName(userInfoResponse.getGivenName());
        authUser.setFamilyName(userInfoResponse.getFamilyName());
        authUser.setNickname(userInfoResponse.getNickname());
        authUser.setName(userInfoResponse.getName());
        authUser.setPictureUrl(userInfoResponse.getPictureUrl());
        authUser.setLocale(userInfoResponse.getLocale());
        authUser.setUpdatedAt(userInfoResponse.getUpdatedAt());
        authUser.setEmail(userInfoResponse.getEmail());
        authUser.setEmailVerified(userInfoResponse.isEmailVerified());
        authUser.setAppMetadata(userInfoResponse.getAppMetadata());
        return authUser;
    }

    private AuthUserDTO getAuthUserDTO(AuthUser authUser) {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setSub(authUser.getSub());
        authUserDTO.setGivenName(authUser.getGivenName());
        authUserDTO.setFamilyName(authUser.getFamilyName());
        authUserDTO.setNickname(authUser.getNickname());
        authUserDTO.setName(authUser.getName());
        authUserDTO.setPictureUrl(authUser.getPictureUrl());
        authUserDTO.setLocale(authUser.getLocale());
        authUserDTO.setUpdatedAt(authUser.getUpdatedAt());
        authUserDTO.setEmail(authUser.getEmail());
        authUserDTO.setEmailVerified(authUser.isEmailVerified());
        authUserDTO.setAppMetadata(authUser.getAppMetadata());
        return authUserDTO;
    }

    @TestConfiguration
    static class AuthServiceImplTestContextConfiguration {
        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private AuthSettings authSettings;

        @Bean
        public AuthService authService(RestTemplate restTemplate, AuthSettings authSettings) {
            return new AuthServiceImpl(restTemplate, authSettings);
        }
    }
}