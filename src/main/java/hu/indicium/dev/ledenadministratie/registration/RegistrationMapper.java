package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeMapper;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper implements Mapper<Registration, RegistrationDTO> {

    private final StudyTypeService studyTypeService;

    private final StudyTypeMapper studyTypeMapper;

    public RegistrationMapper(StudyTypeService studyTypeService, StudyTypeMapper studyTypeMapper) {
        this.studyTypeService = studyTypeService;
        this.studyTypeMapper = studyTypeMapper;
    }

    @Override
    public RegistrationDTO toDTO(Registration registration) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(registration.getId());
        dto.setFirstName(registration.getFirstName());
        dto.setMiddleName(registration.getMiddleName());
        dto.setLastName(registration.getLastName());
        dto.setMailAddress(registration.getMailAddress().toLowerCase());
        dto.setDateOfBirth(registration.getDateOfBirth());
        dto.setPhoneNumber(registration.getPhoneNumber());
        dto.setStudyType(studyTypeMapper.toDTO(registration.getStudyType()));
        dto.setToReceiveNewsletter(registration.isToReceiveNewsletter());
        dto.setApproved(registration.isApproved());
        dto.setComment(registration.getComment());
        dto.setCreated(registration.getCreated());
        dto.setUpdated(registration.getUpdated());
        dto.setFinalizedAt(registration.getFinalizedAt());
        dto.setFinalizedBy(registration.getFinalizedBy());
        return dto;
    }

    @Override
    public Registration toEntity(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();
        registration.setId(registrationDTO.getId());
        registration.setFirstName(registrationDTO.getFirstName());
        registration.setMiddleName(registrationDTO.getMiddleName());
        registration.setLastName(registrationDTO.getLastName());
        registration.setMailAddress(registrationDTO.getMailAddress().toLowerCase());
        registration.setDateOfBirth(registrationDTO.getDateOfBirth());
        registration.setPhoneNumber(registrationDTO.getPhoneNumber());
        registration.setStudyType(studyTypeMapper.toEntity(studyTypeService.getStudyTypeById(registrationDTO.getStudyType().getId())));
        registration.setToReceiveNewsletter(registrationDTO.isToReceiveNewsletter());
        registration.setApproved(registrationDTO.isApproved());
        registration.setComment(registrationDTO.getComment());
        registration.setCreated(registrationDTO.getCreated());
        registration.setUpdated(registrationDTO.getUpdated());
        registration.setFinalizedAt(registrationDTO.getFinalizedAt());
        registration.setFinalizedBy(registrationDTO.getFinalizedBy());
        return registration;
    }
}