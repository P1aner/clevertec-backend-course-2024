package ru.clevertec.starter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.clevertec.starter.configuration.StarterConfig;
import ru.clevertec.starter.exception.SessionGeneratedException;
import ru.clevertec.starter.model.Session;

@Component
public class SessionListener {

    private final StarterConfig starterConfig;
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public SessionListener(StarterConfig starterConfig) {
        this.starterConfig = starterConfig;
        this.restClient = RestClient.create();
        this.objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public Session getSession(String login) {
        String uri = starterConfig.protocol()
                + starterConfig.domain()
                + starterConfig.pathToObtainSession()
                + starterConfig.loginParameter();
        return restClient
                .get()
                .uri(uri, login)
                .exchange((request, response) -> switch (response.getStatusCode()) {
                    case HttpStatus.OK:
                        yield objectMapper.readValue(response.getBody(), Session.class);
                    case HttpStatus.NOT_FOUND:
                        yield createSession(login);
                    default:
                        throw new SessionGeneratedException("Session server problem");
                });
    }

    @SneakyThrows
    private Session createSession(String login) {
        String uri = starterConfig.protocol()
                + starterConfig.domain()
                + starterConfig.pathToCreateSession()
                + starterConfig.loginParameter();
        return restClient
                .post()
                .uri(uri, login)
                .exchange((request, response) -> switch (response.getStatusCode()) {
                    case HttpStatus.CREATED:
                        yield objectMapper.readValue(response.getBody(), Session.class);
                    default:
                        throw new SessionGeneratedException("Session not created");
                });
    }
}
