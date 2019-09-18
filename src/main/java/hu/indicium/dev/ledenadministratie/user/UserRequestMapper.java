package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.requests.CreateUserRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class UserRequestMapper {
    public static UserDTO toDTO(CreateUserRequest createUserRequest) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(createUserRequest, UserDTO.class);
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(createUserRequest.getStudyTypeId());
        userDTO.setStudyType(studyTypeDTO);
        return userDTO;
    }
}
