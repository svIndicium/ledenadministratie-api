package hu.indicium.dev.ledenadministratie.setting;

import hu.indicium.dev.ledenadministratie.setting.dto.SettingDTO;
import hu.indicium.dev.ledenadministratie.setting.requests.UpdateSettingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/settings/lit")
public class SettingController {
    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<SettingDTO> getSettings() {
        return settingService.getAllSettings();
    }

    @PutMapping(value = "/{settingKey}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SettingDTO updateSetting(@PathVariable String settingKey, @RequestBody @Valid UpdateSettingRequest updateSettingRequest) {
        return settingService.updateSetting(settingKey, updateSettingRequest.getValue());
    }
}
