package hu.indicium.dev.ledenadministratie.auth;

import hu.indicium.dev.ledenadministratie.auth.dto.AuthUserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthUserMapper implements Mapper<AuthUser, AuthUserDTO> {

    private final ModelMapper modelMapper;

    public AuthUserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthUserDTO toDTO(AuthUser entity) {
        return modelMapper.map(entity, AuthUserDTO.class);
    }

    @Override
    public AuthUser toEntity(AuthUserDTO dto) {
        return modelMapper.map(dto, AuthUser.class);
    }
}
