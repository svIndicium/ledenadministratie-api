package hu.indicium.dev.ledenadministratie.registration;

import hu.indicium.dev.ledenadministratie.registration.dto.RegistrationDTO;
import hu.indicium.dev.ledenadministratie.studytype.StudyType;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeMapper;
import hu.indicium.dev.ledenadministratie.studytype.StudyTypeService;
import hu.indicium.dev.ledenadministratie.util.Mapper;
import org.springframework.stereotype.Component;

public class RegistrationMapper {

    public static RegistrationDTO map(Registration registration) {
        RegistrationDTO dto = new RegistrationDTO();
        dto.setId(registration.getId());
        dto.setFirstName(registration.getFirstName());
        dto.setMiddleName(registration.getMiddleName());
        dto.setLastName(registration.getLastName());
        dto.setMailAddress(registration.getMailAddress().toLowerCase());
        dto.setDateOfBirth(registration.getDateOfBirth());
        dto.setPhoneNumber(registration.getPhoneNumber());
        dto.setStudyTypeId(registration.getStudyType().getId());
        dto.setToReceiveNewsletter(registration.isToReceiveNewsletter());
        dto.setApproved(registration.isApproved());
        dto.setComment(registration.getComment());
        dto.setCreated(registration.getCreated());
        dto.setUpdated(registration.getUpdated());
        dto.setFinalizedAt(registration.getFinalizedAt());
        dto.setFinalizedBy(registration.getFinalizedBy());
        dto.setVerifiedAt(registration.getVerifiedAt());
        dto.setVerificationRequestedAt(registration.getVerificationRequestedAt());
        dto.setVerificationToken(registration.getVerificationToken());
        return dto;
    }

    public static Registration map(RegistrationDTO registrationDTO) {
        Registration registration = new Registration();
        registration.setId(registrationDTO.getId());
        registration.setFirstName(registrationDTO.getFirstName());
        registration.setMiddleName(registrationDTO.getMiddleName());
        registration.setLastName(registrationDTO.getLastName());
        registration.setMailAddress(registrationDTO.getMailAddress().toLowerCase());
        registration.setDateOfBirth(registrationDTO.getDateOfBirth());
        registration.setPhoneNumber(registrationDTO.getPhoneNumber());
        registration.setStudyType(new StudyType(registrationDTO.getStudyTypeId()));
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