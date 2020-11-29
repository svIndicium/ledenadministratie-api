package hu.indicium.dev.ledenadministratie.setting;

import hu.indicium.dev.ledenadministratie.infrastructure.auth.Auth0User;
import hu.indicium.dev.ledenadministratie.infrastructure.auth.AuthService;
import hu.indicium.dev.ledenadministratie.setting.dto.SettingDTO;
import hu.indicium.dev.ledenadministratie.setting.exceptions.SettingNotFoundException;
import hu.indicium.dev.ledenadministratie.setting.exceptions.SettingNotSetException;
import hu.indicium.dev.ledenadministratie.util.WithMockToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("Settings service")
class SettingServiceImplTest {

    @MockBean
    private SettingRepository settingRepository;

    @MockBean
    private AuthService authService;

    @Autowired
    private SettingService settingService;

    private Setting setting;

    private Auth0User auth0User;

    @BeforeEach
    void setUp() {
        setting = new Setting();
        setting.setKey("key");
        setting.setValue("value");
        setting.setPermission("key");
        setting.setDescription("This is a description");
        setting.setTitle("This is the title");
        setting.setUpdatedBy("John doe");

        auth0User = new Auth0User();
        auth0User.setName("Jane Doe");
    }

    @Test
    @DisplayName("Get value with key")
    void shouldReturnStringWithValue_whenGetSettingByKey() {
        when(settingRepository.findByKey(eq("key"))).thenReturn(Optional.of(setting));

        String value = settingService.getValueByKey("key");

        assertThat(value).isEqualTo(setting.getValue());
    }

    @Test
    @DisplayName("Get setting")
    void shouldReturnSetting_whenGetSetting() {
        when(settingRepository.findByKey(eq("key"))).thenReturn(Optional.of(setting));

        SettingDTO settingDTO = settingService.getSettingByKey("key");

        assertThat(settingDTO).isEqualToComparingFieldByField(setting);
    }

    @Test
    @DisplayName("Get empty setting")
    void shouldThrowException_whenGetEmptySetting() {
        setting.setValue("");
        when(settingRepository.findByKey(eq("key"))).thenReturn(Optional.of(setting));

        try {
            settingService.getSettingByKey("key");
            fail("The value of the setting is empty");
        } catch (Exception e) {
            assertThat(e).isOfAnyClassIn(SettingNotSetException.class);
        }
    }

    @Test
    @DisplayName("Get non-existing setting")
    void shouldThrowException_whenGetNonExistingSetting() {
        try {
            settingService.getSettingByKey("key");
            fail("The setting does not exist");
        } catch (Exception e) {
            assertThat(e).isOfAnyClassIn(SettingNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Update setting")
    @WithMockToken(scope = "write:key")
    void shouldUpdateSettingCorrectly_whenUpdatingSetting() {
        ArgumentCaptor<Setting> settingArgumentCaptor = ArgumentCaptor.forClass(Setting.class);

        when(authService.getCurrentUser()).thenReturn(auth0User);
        when(settingRepository.findByKey(eq("key"))).thenReturn(Optional.of(setting));
        when(settingRepository.save(settingArgumentCaptor.capture())).thenReturn(setting);

        String newValue = "newValue";

        SettingDTO settingDTO = settingService.updateSetting("key", newValue);

        Setting capturedSetting = settingArgumentCaptor.getValue();

        assertThat(capturedSetting.getValue()).isEqualTo(newValue);
        assertThat(capturedSetting.getUpdatedBy()).isEqualTo(auth0User.getName());
        assertThat(capturedSetting.getKey()).isEqualTo(setting.getKey());
        assertThat(capturedSetting.getDescription()).isEqualTo(setting.getDescription());
        assertThat(capturedSetting.getTitle()).isEqualTo(setting.getTitle());
    }

    @Test
    @DisplayName("Update non-existing setting")
    @WithMockToken(scope = "write:key")
    void shouldThrowException_whenUpdateNonExistingSetting() {

        when(authService.getCurrentUser()).thenReturn(auth0User);

        try {
            settingService.updateSetting("key", "newValue");
            fail("The setting does not exist");
        } catch (Exception e) {
            assertThat(e).isOfAnyClassIn(SettingNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Get settings")
    @WithMockToken(scope = {"read:key", "read:settings"})
    void shouldReturnListOfSettings_whenGetAllSettings() {
        Setting setting1 = new Setting();
        setting1.setKey("key");
        setting1.setValue("value");
        setting1.setPermission("keys");
        setting1.setDescription("This is a description");
        setting1.setTitle("This is the title");
        setting1.setUpdatedBy("John doe");

        when(settingRepository.findAll()).thenReturn(Arrays.asList(setting, setting1));

        List<SettingDTO> settings = settingService.getAllSettings();

        assertThat(settings).hasSize(1);
        assertThat(settings.get(0)).isEqualToComparingFieldByField(setting);
    }

    @TestConfiguration
    static class SettingServiceTestContextConfiguration {

        @Autowired
        private SettingRepository settingRepository;

        @Autowired
        private AuthService authService;

        @Bean
        public SettingService settingService() {
            return new SettingServiceImpl(settingRepository, authService);
        }
    }
}