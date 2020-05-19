package hu.indicium.dev.ledenadministratie.group.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
}
