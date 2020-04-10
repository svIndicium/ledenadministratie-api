package hu.indicium.dev.ledenadministratie.setting;

import hu.indicium.dev.ledenadministratie.setting.dto.SettingDTO;

import java.util.List;

public interface SettingService {
    SettingDTO getSettingByKey(String key);

    SettingDTO updateSetting(String key, String value);

    List<SettingDTO> getAllSettings();
}
