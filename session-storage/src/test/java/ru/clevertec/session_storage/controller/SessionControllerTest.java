package ru.clevertec.session_storage.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import ru.clevertec.session_storage.model.Session;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SessionControllerTest {

    @LocalServerPort
    private int port;
    private final RestClient restClient = RestClient.create();
    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:8.0.1"));

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        mongoDBContainer.start();
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void testCreateSession() {
        ResponseEntity<Session> response = executeCreateSessionRequest(TestData.LOGIN1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(TestData.getSession1()).isEqualTo(response.getBody());

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> executeCreateSessionRequest(TestData.LOGIN1));

        assertTrue(httpClientErrorException.getMessage().contains("Session is opened"));
        assertTrue(httpClientErrorException.getMessage().contains("400"));
    }

    @Test
    void testGetSession() {

        HttpClientErrorException httpClientErrorException = Assert.assertThrows(HttpClientErrorException.class, () -> executeGetSessionRequest(TestData.LOGIN2));
        assertTrue(httpClientErrorException.getMessage().contains("Session not found"));
        assertTrue(httpClientErrorException.getMessage().contains("404"));

        executeCreateSessionRequest(TestData.LOGIN2);
        ResponseEntity<Session> response = executeGetSessionRequest(TestData.LOGIN2);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(TestData.getSession2());
        assertThat(Objects.requireNonNull(response.getBody()).getLogin()).isEqualTo(TestData.LOGIN2);
    }

    private ResponseEntity<Session> executeCreateSessionRequest(String login) {
        String uri = String.format(TestData.CREATE_URL, port, login);
        return restClient
                .post()
                .uri(uri)
                .retrieve()
                .toEntity(Session.class);
    }

    private ResponseEntity<Session> executeGetSessionRequest(String login) {
        String uri = String.format(TestData.GET_URL, port, login);
        return restClient
                .get()
                .uri(uri)
                .retrieve()
                .toEntity(Session.class);
    }
}