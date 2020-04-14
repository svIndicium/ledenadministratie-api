package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;

public class AuthUserMapper {

    public static AuthUserDTO map(AuthUser entity) {
        AuthUserDTO authUserDTO = new AuthUserDTO();
        authUserDTO.setSub(entity.getSub());
        authUserDTO.setName(entity.getName());
        authUserDTO.setAppMetadata(entity.getAppMetadata());
        authUserDTO.setEmail(entity.getEmail());
        authUserDTO.setEmailVerified(entity.isEmailVerified());
        authUserDTO.setFamilyName(entity.getFamilyName());
        authUserDTO.setGivenName(entity.getGivenName());
        authUserDTO.setPictureUrl(entity.getPictureUrl());
        authUserDTO.setUpdatedAt(entity.getUpdatedAt());
        authUserDTO.setLocale(entity.getLocale());
        authUserDTO.setNickname(entity.getNickname());
        return authUserDTO;
    }

    public static AuthUser map(AuthUserDTO dto) {
        AuthUser authUser = new AuthUser();
        authUser.setSub(dto.getSub());
        authUser.setName(dto.getName());
        authUser.setAppMetadata(dto.getAppMetadata());
        authUser.setEmail(dto.getEmail());
        authUser.setEmailVerified(dto.isEmailVerified());
        authUser.setFamilyName(dto.getFamilyName());
        authUser.setGivenName(dto.getGivenName());
        authUser.setPictureUrl(dto.getPictureUrl());
        authUser.setUpdatedAt(dto.getUpdatedAt());
        authUser.setLocale(dto.getLocale());
        authUser.setNickname(dto.getNickname());
        return authUser;
    }
}
