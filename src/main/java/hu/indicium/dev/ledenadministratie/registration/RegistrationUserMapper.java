package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.user.dto.UserDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RegistrationUserMapper implements Mapper<RegistrationDTO, UserDTO> {

    private final ModelMapper modelMapper;

    public RegistrationUserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO toDTO(RegistrationDTO registration) {
        UserDTO userDTO = modelMapper.map(registration, UserDTO.class);
        userDTO.setId(null);
        return userDTO;
    }

    @Override
    public RegistrationDTO toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, RegistrationDTO.class);
    }
}
