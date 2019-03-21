package hu.indicium.dev.lit.study.dto;

import java.util.Date;

public class StudyDTO {
    private Long id;
    private Date startDate;
    private StudyTypeDTO type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public StudyTypeDTO getType() {
        return type;
    }

    public void setType(StudyTypeDTO type) {
        this.type = type;
    }
}
