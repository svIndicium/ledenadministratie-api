package hu.indicium.dev.lit.register.mapper;

import hu.indicium.dev.lit.register.Registration;
import hu.indicium.dev.lit.register.dto.RegistrationDTO;
import hu.indicium.dev.lit.util.Mapper;

public class RegistrationMapper implements Mapper<Registration, RegistrationDTO> {
    @Override
    public Registration toEntity(RegistrationDTO dto) {
        return null;
    }

    @Override
    public RegistrationDTO toDTO(Registration entity) {
        return null;
    }
}
