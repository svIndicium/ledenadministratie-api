package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.auth.requests.UserInfoRequest;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("AuthUser Mapper")
class AuthUserMapperTest {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Mapper<AuthUser, AuthUserDTO> authUserMapper;

    @Test
    @DisplayName("Convert to DTO")
    void shouldReturnCorrectDTO_whenConvertEntityToDTO() {
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setSub("google-oauth2");
        userInfoRequest.setGivenName("John");
        userInfoRequest.setFamilyName("Doe");
        userInfoRequest.setNickname("Johndoe123");
        userInfoRequest.setName("John Doe");
        userInfoRequest.setPictureUrl("https://kekekek.com/img.url");
        userInfoRequest.setLocale("nl");
        userInfoRequest.setUpdatedAt(new Date());
        userInfoRequest.setEmail("john@doe.com");
        userInfoRequest.setEmailVerified(true);
        AuthUser authUser = new AuthUser(userInfoRequest);

        AuthUserDTO authUserDTO = authUserMapper.toDTO(authUser);

        assertThat(authUserDTO).isEqualToComparingFieldByField(userInfoRequest);
    }

    @Test
    @DisplayName("Convert to entity")
    void shouldReturnCorrectEntity_whenConvertDTOToEntity() {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setSub("google-oauth2");
        authUserDTO.setGivenName("John");
        authUserDTO.setFamilyName("Doe");
        authUserDTO.setNickname("Johndoe123");
        authUserDTO.setName("John Doe");
        authUserDTO.setPictureUrl("https://kekekek.com/img.url");
        authUserDTO.setLocale("nl");
        authUserDTO.setUpdatedAt(new Date());
        authUserDTO.setEmail("john@doe.com");
        authUserDTO.setEmailVerified(true);

        AuthUser authUser = authUserMapper.toEntity(authUserDTO);

        assertThat(authUserDTO).isEqualToComparingFieldByField(authUser);
    }

    @TestConfiguration
    static class AuthUserMapperTestContextConfiguration {
        @Autowired
        private ModelMapper modelMapper;

        @Bean
        public Mapper<AuthUser, AuthUserDTO> authUserMapper() {
            return new AuthUserMapper(modelMapper);
        }
    }
}