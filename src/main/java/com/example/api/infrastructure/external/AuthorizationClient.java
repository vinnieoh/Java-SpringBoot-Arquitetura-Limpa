package com.example.api.infrastructure.external;

import com.example.api.application.gateways.AuthorizationGateway;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.example.api.infrastructure.config.configs.Configs.AUTHORIZATION_URL;

@Component
public class AuthorizationClient implements AuthorizationGateway {

    private final RestTemplate restTemplate;

    public AuthorizationClient() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public boolean isAuthorized() {
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(AUTHORIZATION_URL, Map.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                String message = (String) response.getBody().get("message");
                return "Autorizado".equalsIgnoreCase(message);
            }

        } catch (RestClientException e) {
            // Log e fallback podem ser aplicados aqui
            System.err.println("Erro ao consultar autorização externa: " + e.getMessage());
        }

        return false; // Se falhar, considere como não autorizado
    }
}

