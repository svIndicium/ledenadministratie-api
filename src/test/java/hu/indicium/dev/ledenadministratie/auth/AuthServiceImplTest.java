package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.auth.requests.UserInfoRequest;
import hu.indicium.dev.ledenadministratie.util.Mapper;
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
    private Mapper<AuthUser, AuthUserDTO> authUserMapper;

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
        when(authSettings.getIssuer()).thenReturn("https://indicium.eu.auth0.com");

        UserInfoRequest userInfoRequest = getUserInfoRequest();

        when(restTemplate.exchange(eq("https://indicium.eu.auth0.com/userinfo"), eq(HttpMethod.GET), httpEntityArgumentCaptor.capture(), eq(UserInfoRequest.class))).thenReturn(ResponseEntity.of(Optional.of(userInfoRequest)));

        AuthUser authUser = getAuthUser(userInfoRequest);

        when(authUserMapper.toDTO(eq(authUser))).thenReturn(getAuthUserDTO(authUser));

        AuthUserDTO authUserDTO = authService.getAuthUser();

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
        when(authSettings.getIssuer()).thenReturn("https://indicium.eu.auth0.com");

        UserInfoRequest userInfoRequest = getUserInfoRequest();

        when(restTemplate.exchange(eq("https://indicium.eu.auth0.com/userinfo"), eq(HttpMethod.GET), httpEntityArgumentCaptor.capture(), eq(UserInfoRequest.class))).thenReturn(ResponseEntity.status(401).body(null));
        try {
            AuthUserDTO authUserDTO = authService.getAuthUser();
            fail("User should not be able to identify itself");
        } catch (Exception e) {
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    private UserInfoRequest getUserInfoRequest() {
        Map<String, Object> appMetadata = new HashMap<>();
        appMetadata.put("id", 1);
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setSub("sub");
        userInfoRequest.setGivenName("John");
        userInfoRequest.setFamilyName("Doe");
        userInfoRequest.setNickname("johndoe123");
        userInfoRequest.setName("John Doe");
        userInfoRequest.setPictureUrl("");
        userInfoRequest.setLocale("nl");
        userInfoRequest.setUpdatedAt(new Date());
        userInfoRequest.setEmail("john@doe.com");
        userInfoRequest.setEmailVerified(true);
        userInfoRequest.setAppMetadata(appMetadata);
        return userInfoRequest;
    }

    private AuthUser getAuthUser(UserInfoRequest userInfoRequest) {
        AuthUser authUser = new AuthUser();
        authUser.setSub(userInfoRequest.getSub());
        authUser.setGivenName(userInfoRequest.getGivenName());
        authUser.setFamilyName(userInfoRequest.getFamilyName());
        authUser.setNickname(userInfoRequest.getNickname());
        authUser.setName(userInfoRequest.getName());
        authUser.setPictureUrl(userInfoRequest.getPictureUrl());
        authUser.setLocale(userInfoRequest.getLocale());
        authUser.setUpdatedAt(userInfoRequest.getUpdatedAt());
        authUser.setEmail(userInfoRequest.getEmail());
        authUser.setEmailVerified(userInfoRequest.isEmailVerified());
        authUser.setAppMetadata(userInfoRequest.getAppMetadata());
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
        private Mapper<AuthUser, AuthUserDTO> authUserMapper;

        @Autowired
        private AuthSettings authSettings;

        @Bean
        public AuthService authService(RestTemplate restTemplate, Mapper<AuthUser, AuthUserDTO> authUserMapper, AuthSettings authSettings) {
            return new AuthServiceImpl(restTemplate, authUserMapper, authSettings);
        }
    }
}