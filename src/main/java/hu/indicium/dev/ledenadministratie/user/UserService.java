//package hu.indicium.dev.ledenadministratie.user;
//
//import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
//import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
//import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
//
//import java.util.List;
//
//public interface UserService {
//    UserDTO createUser(RegistrationDTO registrationDTO);
//
//    UserDTO updateUser(UserDTO userDTO);
//
//    UserDTO getUserById(Long userId);
//
//    UserDTO getUserByAuthId(String userId);
//
//    List<MailAddressDTO> getMailAddressesByAuthId(String userId);
//
//    List<UserDTO> getUsers();
//
//    UserDTO addMailAddressToUser(Long userId, MailAddressDTO mailAddressDTO);
//
//    MailAddressDTO requestNewMailVerification(Long userId, Long mailId);
//
//    void requestResetPasswordMail(Long userId);
//
//    UserDTO createAuthAccountForUser(Long userId);
//
//    List<MailAddressDTO> getMailAddressesByUserId(Long userId);
//}
//
