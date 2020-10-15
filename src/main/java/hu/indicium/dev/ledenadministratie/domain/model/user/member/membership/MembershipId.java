package hu.indicium.dev.ledenadministratie.domain.model.user.member.membership;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
public class MembershipId implements Serializable {

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "membership_id")
    private UUID id;

    private MembershipId(UUID id) {
        this.id = id;
    }

    public static MembershipId fromId(UUID uuid) {
        return new MembershipId(uuid);
    }
}
