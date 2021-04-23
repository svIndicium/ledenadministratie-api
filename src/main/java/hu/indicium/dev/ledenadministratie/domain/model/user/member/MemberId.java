package hu.indicium.dev.ledenadministratie.domain.model.user.member;

import hu.indicium.dev.ledenadministratie.domain.model.user.registration.RegistrationId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor
@Getter
public class MemberId implements Serializable {

    @Column(name = "auth_id", unique = true)
    private String authId;

    public MemberId(String authId) {
        this.authId = authId;
    }

    public static MemberId fromAuthId(String authId) {
        return new MemberId(authId);
    }

    public static MemberId fromRegistrationId(RegistrationId registrationId) {
        return new MemberId(registrationId.getId().toString());
    }
    public static MemberId fromUuid(UUID uuid) {
        return new MemberId(uuid.toString());
    }
}
