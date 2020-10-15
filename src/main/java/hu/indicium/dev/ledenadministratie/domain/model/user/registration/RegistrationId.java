package hu.indicium.dev.ledenadministratie.domain.model.user.registration;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
public class RegistrationId implements Serializable {

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "registration_id")
    private final UUID id;

    private RegistrationId(UUID id) {
        this.id = id;
    }

    public static RegistrationId fromId(UUID id) {
        return new RegistrationId(id);
    }
}
