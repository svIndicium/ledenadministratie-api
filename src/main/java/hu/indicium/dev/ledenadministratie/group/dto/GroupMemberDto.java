package hu.indicium.dev.ledenadministratie.group.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GroupMemberDto {
    private Long id;

    private String userId;

    private String fullName;

    private Date startDate;

    private Date endDate;

    private Date createdAt;

    private Date updatedAt;

    private Long groupId;
}
