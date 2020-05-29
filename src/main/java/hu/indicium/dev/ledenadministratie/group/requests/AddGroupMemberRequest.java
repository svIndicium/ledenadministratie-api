package hu.indicium.dev.ledenadministratie.group.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AddGroupMemberRequest {
    private String userId;

    private Date startDate;

    private Date endDate;
}
