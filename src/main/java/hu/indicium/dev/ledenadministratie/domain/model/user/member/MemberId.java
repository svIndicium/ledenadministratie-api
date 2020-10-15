package hu.indicium.dev.ledenadministratie.domain.model.user.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

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
}
