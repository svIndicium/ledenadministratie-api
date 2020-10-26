package hu.indicium.dev.ledenadministratie.infrastructure.web.dto;

import hu.indicium.dev.ledenadministratie.domain.model.user.MemberDetails;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class MemberDetailsDto {

    private NameDto name;

    private String phoneNumber;

    private Date dateOfBirth;

    private UUID studyTypeId;

    private Date createdAt;

    public MemberDetailsDto(MemberDetails memberDetails) {
        this.name = new NameDto(memberDetails.getName());
        this.phoneNumber = memberDetails.getPhoneNumber();
        this.dateOfBirth = memberDetails.getDateOfBirth();
        this.studyTypeId = memberDetails.getStudyType().getStudyTypeId().getId();
        this.createdAt = memberDetails.getCreatedAt();
    }
}
