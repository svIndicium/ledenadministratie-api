package hu.indicium.dev.ledenadministratie.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Name {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public String getFullName() {
        return String.format("%s %s", firstName, getFullLastName());
    }

    public String getFullLastName() {
        if (middleName != null) {
            return String.format("%s %s", middleName, lastName);
        }
        return lastName;
    }
}
