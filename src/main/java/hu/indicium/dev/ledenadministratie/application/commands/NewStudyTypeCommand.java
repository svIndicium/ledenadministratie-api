package hu.indicium.dev.ledenadministratie.application.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewStudyTypeCommand {
    private String shortName;

    private String name;
}
