package hu.indicium.dev.ledenadministratie.setting.exceptions;

public class SettingNotSetException extends RuntimeException {
    public SettingNotSetException(String key) {
        super(String.format("Setting with key %s is not set", key));
    }
}
