package hu.indicium.dev.ledenadministratie.infrastructure.auth;

import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import hu.indicium.dev.ledenadministratie.domain.model.user.mailaddress.MailAddress;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepresentationFactory {
    public static UserRepresentation create(MemberDetails memberDetails, MailAddress mailAddress) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(memberDetails.getName().getFirstName());
        userRepresentation.setLastName(memberDetails.getName().getFullLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(mailAddress.getAddress());
        userRepresentation.setGroups(Arrays.asList("Leden"));
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("studyTypeId", Arrays.asList(memberDetails.getStudyType().getStudyTypeId().getId().toString()));
        userRepresentation.setAttributes(attributes);
        userRepresentation.setEmail(mailAddress.getAddress());
        userRepresentation.setEmailVerified(true);
        return userRepresentation;
    }
}
