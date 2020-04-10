package hu.indicium.dev.ledenadministratie.setting.requests;

import javax.validation.constraints.NotBlank;

public class UpdateSettingRequest {
    @NotBlank
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}