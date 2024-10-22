package ru.clevertec.starter.bpp.test_data;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.clevertec.starter.model.Session;

public class TestData {
    public static final String LOGIN_LOGIN = "login";
    public static final String GET_LOGIN_REQUEST = "/sessions/current?login=";
    public static final String CREATE_LOGIN_REQUEST = "/sessions/create?login=";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonSession() {
        Session session = new Session(TestData.LOGIN_LOGIN);
        String jsonSession = null;
        try {
            jsonSession = objectMapper.writeValueAsString(session);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonSession;
    }
}
