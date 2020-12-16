package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Arrays;

public class UserRepresentationFactory {
    public static UserRepresentation create(MemberDetails memberDetails, MailAddress mailAddress) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(memberDetails.getName().getFirstName());
        userRepresentation.setLastName(memberDetails.getName().getFullLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(mailAddress.getAddress());
        userRepresentation.setGroups(Arrays.asList("Leden"));
        userRepresentation.setEmail(mailAddress.getAddress());
        userRepresentation.setEmailVerified(true);
        return userRepresentation;
    }
}
