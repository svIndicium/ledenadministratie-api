package hu.indicium.dev.ledenadministratie.domain.model.user;

import hu.indicium.dev.ledenadministratie.domain.AssertionConcern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Name extends AssertionConcern {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public Name(String firstName, String middleName, String lastName) {
        this.setFirstName(firstName);
        this.setMiddleName(middleName);
        this.setLastName(lastName);
    }

    public String getFullName() {
        return String.format("%s %s", firstName, getFullLastName());
    }

    public String getFullLastName() {
        if (middleName != null) {
            return String.format("%s %s", middleName, lastName);
        }
        return lastName;
    }

    public void setFirstName(String firstName) {
        assertArgumentNotNull(firstName, "First name must be given.");
        assertArgumentLength(firstName, 255, "First name is too long.");

        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        if (middleName != null) {
            assertArgumentLength(middleName, 255, "Middle name is too long.");
        }

        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        assertArgumentNotNull(lastName, "Last name must be given.");
        assertArgumentLength(lastName, 255, "Last name is too long.");

        this.lastName = lastName;
    }
}
