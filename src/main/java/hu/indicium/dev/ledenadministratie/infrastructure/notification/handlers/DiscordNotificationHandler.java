package hu.indicium.dev.ledenadministratie.infrastructure.notification.handlers;

import hu.indicium.dev.ledenadministratie.infrastructure.notification.NotificationHandler;
import hu.indicium.dev.ledenadministratie.setting.SettingService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

@AllArgsConstructor
@Log4j2
public class DiscordNotificationHandler implements NotificationHandler {

    private final SettingService settingService;

    @Override
    public void sendNotification(String message) {

        String webhookUrl = settingService.getValueByKey("DISCORD_WEBHOOK_URL");

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response;
        HttpPost request = new HttpPost(webhookUrl);
        request.addHeader("Content-Type", "application/json");
        try {
            String jsonMessage = "{\"content\": \"" + message.replace("\"", "\\\"") + "\"}";
            StringEntity params = new StringEntity(jsonMessage);
            request.setEntity(params);
            response = httpClient.execute(request);
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
