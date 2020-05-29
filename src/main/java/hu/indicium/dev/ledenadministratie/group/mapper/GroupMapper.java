package hu.indicium.dev.ledenadministratie.group.mapper;

import hu.indicium.dev.ledenadministratie.group.domain.Group;
import hu.indicium.dev.ledenadministratie.group.dto.GroupDTO;

public class GroupMapper {
    public static GroupDTO map(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setDescription(group.getDescription());
        groupDTO.setUpdatedAt(group.getUpdatedAt());
        groupDTO.setCreatedAt(group.getUpdatedAt());
        return groupDTO;
    }
}
