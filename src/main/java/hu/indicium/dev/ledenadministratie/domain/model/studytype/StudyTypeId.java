package hu.indicium.dev.ledenadministratie.domain.model.studytype;

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
public class StudyTypeId implements Serializable {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private StudyTypeId(UUID id) {
        this.id = id;
    }

    public static StudyTypeId fromId(UUID id) {
        return new StudyTypeId(id);
    }
}
