package hu.indicium.dev.ledenadministratie.group.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;

    public GroupDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
