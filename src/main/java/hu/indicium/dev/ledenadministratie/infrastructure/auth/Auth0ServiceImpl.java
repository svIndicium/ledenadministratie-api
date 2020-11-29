package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.tickets.PasswordChangeTicket;
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;
import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import hu.indicium.dev.ledenadministratie.domain.model.user.member.MemberId;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailService;
import hu.indicium.dev.ledenadministratie.infrastructure.mail.MailType;
import hu.indicium.dev.ledenadministratie.infrastructure.notification.NotificationService;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import hu.indicium.dev.ledenadministratie.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Service
public class Auth0ServiceImpl implements AuthService {

    private final Auth0ApiProvider auth0ApiProvider;

    private final NotificationService notificationService;

    private final SettingService settingService;

    private final MailService mailService;

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
        Request<?> request = auth0ApiProvider.getManagementAPI().users().addRoles(user.getId(), Collections.singletonList(settingService.getValueByKey("AUTH0_DEFAULT_ROLE")));
        executeRequest(request);
        MemberId memberId = MemberId.fromAuthId(user.getId());
        requestPasswordReset(memberId, mailAddress, memberDetails.getName());
        return memberId;
    }

    @Override
    public void requestPasswordReset(MemberId memberId, MailAddress mailAddress, Name name) {
        PasswordChangeTicket passwordChangeTicket = new PasswordChangeTicket(memberId.getAuthId());
        Request<PasswordChangeTicket> passwordChangeTicketRequest = auth0ApiProvider.getManagementAPI().tickets().requestPasswordChange(passwordChangeTicket);
        PasswordChangeTicket ticket = executeRequest(passwordChangeTicketRequest);
        Map<String, Object> params = new HashMap<>();
        params.put("PASSWORD_RESET_LINK", ticket.getTicket());
        mailService.sendMail(mailAddress, name, MailType.RESET_PASSWORD, params);
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
        user.setEmailVerified(true);
        user.setName(memberDetails.getName().getFullName());
        user.setGivenName(memberDetails.getName().getFirstName());
        user.setFamilyName(memberDetails.getName().getFullLastName());
        user.setPassword(Util.generateTemporaryPassword());
        return user;
    }
}
