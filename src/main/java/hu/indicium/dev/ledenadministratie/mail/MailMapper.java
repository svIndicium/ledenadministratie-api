package hu.indicium.dev.ledenadministratie.mail;

import hu.indicium.dev.ledenadministratie.user.dto.MailDTO;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MailMapper implements Mapper<Mail, MailDTO> {

    private final ModelMapper modelMapper;

    public MailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public MailDTO toDTO(Mail entity) {
        return modelMapper.map(entity, MailDTO.class);
    }

    @Override
    public Mail toEntity(MailDTO dto) {
        return modelMapper.map(dto, Mail.class);
    }
}
