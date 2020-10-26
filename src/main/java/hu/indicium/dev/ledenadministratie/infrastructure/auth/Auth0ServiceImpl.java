package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.NotificationService;
import hu.indicium.dev.ledenadministratie.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class Auth0ServiceImpl implements AuthService {

    private final Auth0ApiProvider auth0ApiProvider;

    private final NotificationService notificationService;

    @Override
    public Auth0User getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String authUserId = (String) authentication.getPrincipal();
        Request<User> userRequest = auth0ApiProvider.getManagementAPI().users().get(authUserId, new UserFilter());
        User user = executeRequest(userRequest);
        return new Auth0User(user);
    }

    @Override
    public MemberId createAccountForUser(MemberDetails memberDetails, MailAddress mailAddress) {
        User user = mapToUser(memberDetails, mailAddress);
        Request<User> userRequest = auth0ApiProvider.getManagementAPI().users().create(user);
        user = executeRequest(userRequest);
        return MemberId.fromAuthId(user.getId());
    }

    private <T> T executeRequest(Request<T> request) {
        try {
            return request.execute();
        } catch (Auth0Exception e) {
            notificationService.sendNotification(new Auth0ConnectionNotification(e));
            throw new RuntimeException(e.getMessage());
        }
    }

    private User mapToUser(MemberDetails memberDetails, MailAddress mailAddress) {
        User user = new User("Username-Password-Authentication");
        user.setEmail(mailAddress.getAddress());
        user.setEmailVerified(mailAddress.isVerified());
        user.setName(memberDetails.getName().getFullName());
        user.setGivenName(memberDetails.getName().getFirstName());
        user.setFamilyName(memberDetails.getName().getFullLastName());
        user.setPassword(Util.generateTemporaryPassword());
        return user;
    }
}
