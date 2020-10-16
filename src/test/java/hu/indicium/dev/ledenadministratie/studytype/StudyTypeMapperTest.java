//package hu.indicium.dev.ledenadministratie.studytype;
//
//import hu.indicium.dev.ledenadministratie.domain.model.studytype.StudyType;
//import hu.indicium.dev.ledenadministratie.studytype.dto.StudyTypeDTO;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("Study type mapper")
//class StudyTypeMapperTest {
//
//    @Test
//    @DisplayName("Convert entity to DTO")
//    void shouldReturnCorrectDTO_whenConvertEntityToDTO() {
//        StudyType studyType = new StudyType("Software Development");
//        studyType.setId(1L);
//
//        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
//        studyTypeDTO.setId(studyType.getId());
//        studyTypeDTO.setName(studyType.getName());
//
//        StudyTypeMapper studyTypeMapper = new StudyTypeMapper();
//
//        StudyTypeDTO createdDTO = studyTypeMapper.toDTO(studyType);
//
//        assertThat(createdDTO).isNotNull();
//        assertThat(createdDTO).isEqualToComparingFieldByField(studyTypeDTO);
//    }
//
//    @Test
//    @DisplayName("Convert DTO to entity")
//    void shouldReturnCorrectEntity_whenConvertToEntity() {
//        StudyType studyType = new StudyType("Software Development");
//        studyType.setId(1L);
//
//        StudyTypeDTO studyTypeDTO = new StudyTypeDTO();
//        studyTypeDTO.setId(studyType.getId());
//        studyTypeDTO.setName(studyType.getName());
//
//        StudyTypeMapper studyTypeMapper = new StudyTypeMapper();
//
//        StudyType createdStudyType = studyTypeMapper.toEntity(studyTypeDTO);
//
//        assertThat(createdStudyType).isNotNull();
//        assertThat(createdStudyType).isEqualToComparingFieldByField(studyType);
//    }
//
//}