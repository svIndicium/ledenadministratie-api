package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.user.MailAddress;
import hu.indicium.dev.ledenadministratie.user.dto.MailAddressDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MailMapper implements Mapper<MailAddress, MailAddressDTO> {

    private final ModelMapper modelMapper;

    public MailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MailAddressDTO toDTO(MailAddress entity) {
        return modelMapper.map(entity, MailAddressDTO.class);
    }

    @Override
    public MailAddress toEntity(MailAddressDTO dto) {
        return modelMapper.map(dto, MailAddress.class);
    }
}
