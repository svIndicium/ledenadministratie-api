//package hu.indicium.dev.ledenadministratie.user;
//
//import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
//import hu.indicium.dev.ledenadministratie.user.requests.UpdateUserRequest;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//
//public class UserRequestMapper {
//
//    private ModelMapper modelMapper = new ModelMapper();
//
//    public UserDTO toDTO(UpdateUserRequest updateUserRequest) {
//        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        UserDTO userDTO = modelMapper.map(updateUserRequest, UserDTO.class);
//        userDTO.setStudyTypeId(updateUserRequest.getStudyTypeId());
//        return userDTO;
//    }
//}
