package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@AllArgsConstructor
public class OpenIDConnectService implements AuthService {

    private final WebClient webClient;

    private final KeycloakProvider keycloakProvider;

    @Override
    public User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return new TokenUser(authentication);
    }

    @Override
    public MemberId createAccountForUser(MemberDetails memberDetails, MailAddress mailAddress) {
        try {
            UserRepresentation userRepresentation = UserRepresentationFactory.create(memberDetails, mailAddress);
            Response response = keycloakProvider.getKeycloak()
                    .realm("indicium")
                    .users()
                    .create(userRepresentation);
            String locationUri = response.getLocation().toString();
            String[] parts = locationUri.split("/");
            String id = parts[parts.length - 1];
            return MemberId.fromAuthId(id);
        } catch (Exception e) {
            throw new AuthException(memberDetails.getName().getFullName(), e);
        }
    }

    @Override
    public void requestPasswordReset(MemberId memberId) {
        keycloakProvider.getKeycloak()
                .realm("indicium")
                .users()
                .get(memberId.getAuthId())
                .executeActionsEmail(Collections.singletonList("UPDATE_PASSWORD"));
    }
}
