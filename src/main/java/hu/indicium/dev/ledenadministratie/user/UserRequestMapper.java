package hu.indicium.dev.ledenadministratie.user;

import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.user.requests.CreateUserRequest;
import hu.indicium.dev.ledenadministratie.user.requests.UpdateUserRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class UserRequestMapper {

    private ModelMapper modelMapper = new ModelMapper();

    public UserDTO toDTO(CreateUserRequest createUserRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(createUserRequest, UserDTO.class);
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(createUserRequest.getStudyTypeId());
        userDTO.setStudyType(studyTypeDTO);
        return userDTO;
    }

    public UserDTO toDTO(UpdateUserRequest updateUserRequest) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDTO userDTO = modelMapper.map(updateUserRequest, UserDTO.class);
        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
        studyTypeDTO.setId(updateUserRequest.getStudyTypeId());
        userDTO.setStudyType(studyTypeDTO);
        return userDTO;
    }
}
