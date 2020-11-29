//package hu.indicium.dev.ledenadministratie.auth;
//
//import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
//import hu.indicium.dev.ledenadministratie.auth.responses.UserInfoResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.util.Date;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("AuthUser Mapper")
//class AuthUserMapperTest {
//
//    @Test
//    @DisplayName("Convert to DTO")
//    void shouldReturnCorrectDTO_whenConvertEntityToDTO() {
//        UserInfoResponse userInfoResponse = new UserInfoResponse();
//        userInfoResponse.setSub("google-oauth2");
//        userInfoResponse.setGivenName("John");
//        userInfoResponse.setFamilyName("Doe");
//        userInfoResponse.setNickname("Johndoe123");
//        userInfoResponse.setName("John Doe");
//        userInfoResponse.setPictureUrl("https://kekekek.com/img.url");
//        userInfoResponse.setLocale("nl");
//        userInfoResponse.setUpdatedAt(new Date());
//        userInfoResponse.setEmail("john@doe.com");
//        userInfoResponse.setEmailVerified(true);
//        AuthUser authUser = new AuthUser(userInfoResponse);
//
//        AuthUserDTO authUserDTO = AuthUserMapper.map(authUser);
//
//        assertThat(authUserDTO).isEqualToComparingFieldByField(userInfoResponse);
//    }
//
//    @Test
//    @DisplayName("Convert to entity")
//    void shouldReturnCorrectEntity_whenConvertDTOToEntity() {
//        AuthUserDTO authUserDTO = new AuthUserDTO();
//        authUserDTO.setSub("google-oauth2");
//        authUserDTO.setGivenName("John");
//        authUserDTO.setFamilyName("Doe");
//        authUserDTO.setNickname("Johndoe123");
//        authUserDTO.setName("John Doe");
//        authUserDTO.setPictureUrl("https://kekekek.com/img.url");
//        authUserDTO.setLocale("nl");
//        authUserDTO.setUpdatedAt(new Date());
//        authUserDTO.setEmail("john@doe.com");
//        authUserDTO.setEmailVerified(true);
//
//        AuthUser authUser = AuthUserMapper.map(authUserDTO);
//
//        assertThat(authUserDTO).isEqualToComparingFieldByField(authUser);
//    }
//}