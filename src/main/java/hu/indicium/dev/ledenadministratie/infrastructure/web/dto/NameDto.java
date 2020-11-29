package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.Name;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameDto {
    private String firstName;

    private String middleName;

    private String lastName;

    public NameDto(Name name) {
        this.firstName = name.getFirstName();
        this.middleName = name.getMiddleName();
        this.lastName = name.getLastName();
    }
}
