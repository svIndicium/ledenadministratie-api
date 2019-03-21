package hu.indicium.dev.lit.study.dto;

import java.util.Date;

public class UpdateStudyDTO {
    private Date startDate;
    private TinyStudyTypeDTO type;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public TinyStudyTypeDTO getType() {
        return type;
    }

    public void setType(TinyStudyTypeDTO type) {
        this.type = type;
    }
}
