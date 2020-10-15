//package hu.indicium.dev.ledenadministratie.user;
//
//import hu.indicium.dev.ledenadministratie.domain.model.user.member.Member;
//import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
//import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
//
//public class UserMapper {
//    private UserMapper() {
//    }
//
//    public static UserDTO map(Member user) {
//        UserDTO dto = new UserDTO();
//        dto.setId(user.getId());
//        dto.setFirstName(user.getFirstName());
//        dto.setMiddleName(user.getMiddleName());
//        dto.setLastName(user.getLastName());
//        dto.setDateOfBirth(user.getDateOfBirth());
//        dto.setPhoneNumber(user.getPhoneNumber());
//        dto.setStudyTypeId(user.getStudyType().getId());
//        dto.setUserId(user.getAuth0UserId());
//        return dto;
//    }
//
//    public static Member map(UserDTO userDTO) {
//        Member user = new Member();
//        user.setId(userDTO.getId());
//        user.setFirstName(userDTO.getFirstName());
//        user.setMiddleName(userDTO.getMiddleName());
//        user.setLastName(userDTO.getLastName());
//        user.setDateOfBirth(userDTO.getDateOfBirth());
//        user.setPhoneNumber(userDTO.getPhoneNumber());
//        user.setStudyType(new StudyType(userDTO.getStudyTypeId()));
//        user.setAuth0UserId(userDTO.getUserId());
//        return user;
//    }
//}
