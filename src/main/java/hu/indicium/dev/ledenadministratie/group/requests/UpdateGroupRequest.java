package hu.indicium.dev.ledenadministratie.group.requests;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateGroupRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
