package hu.indicium.dev.ledenadministratie.application.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ImportMemberCommand {
    private String firstName;

    private String middleName;

    private String lastName;

    private String phoneNumber;

    private Date dateOfBirth;

    private String mailAddress;

    private boolean receivingNewsletter;

    private UUID studyTypeId;

    private boolean oldMember;

    private boolean newMember;
}
