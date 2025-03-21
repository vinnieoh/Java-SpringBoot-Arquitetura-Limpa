package com.example.api.infrastructure.external;

import com.example.api.application.gateways.NotificationGateway;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.example.api.infrastructure.config.configs.Configs.NOTIFICATION_URL;

@Component
public class NotificationClient implements NotificationGateway {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void notify(String to, String message) {
        Map<String, String> body = Map.of(
                "email", to,
                "message", message
        );

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body);
        try {
            restTemplate.postForEntity(NOTIFICATION_URL, request, Void.class);
        } catch (Exception e) {
            System.err.println("Falha ao enviar notificação: " + e.getMessage());
            // Aqui você pode logar, ou apenas seguir (notificação é assíncrona normalmente)
        }
    }
}
