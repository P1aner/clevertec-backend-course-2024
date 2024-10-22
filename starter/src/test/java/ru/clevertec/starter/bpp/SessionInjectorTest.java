package ru.clevertec.starter.bpp;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.clevertec.starter.bpp.test_config.TestConfig;
import ru.clevertec.starter.bpp.test_data.TestData;
import ru.clevertec.starter.bpp.test_model.TestSomeSessionRequest;
import ru.clevertec.starter.bpp.test_service.TestService;
import ru.clevertec.starter.exception.LoginBlacklistedException;

@SpringBootTest(classes = {TestConfig.class})
@WireMockTest(httpPort = 7777)
@ContextConfiguration
@TestPropertySource(properties = {"starter.uli-config.port=7777", "starter.blackList=property"})
class SessionInjectorTest {

    @Autowired
    private TestService testService;


    @Test
    void positiveCaseWithOnlyGetSession() {

        String jsonSession = TestData.getJsonSession();

        WireMock.stubFor(WireMock.get(TestData.GET_LOGIN_REQUEST + TestData.LOGIN_LOGIN)
                .willReturn(WireMock.okJson(jsonSession)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        Assertions.assertEquals(TestData.LOGIN_LOGIN, testService.propertyTestSessionInfo(new TestSomeSessionRequest(TestData.LOGIN_LOGIN)));
    }

    @Test
    void positiveCaseWithGetAndPostSession() {

        String jsonSession = TestData.getJsonSession();

        WireMock.stubFor(WireMock.get(TestData.GET_LOGIN_REQUEST + TestData.LOGIN_LOGIN)
                .willReturn(WireMock.notFound()));

        WireMock.stubFor(WireMock.post(TestData.CREATE_LOGIN_REQUEST + TestData.LOGIN_LOGIN)
                .willReturn(WireMock.created().withBody(jsonSession)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)));

        Assertions.assertEquals(TestData.LOGIN_LOGIN, testService.propertyTestSessionInfo(new TestSomeSessionRequest(TestData.LOGIN_LOGIN)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"static", "property"})
    void negativeCaseWithStaticBlocked(String login) {

        Executable executable = () -> testService.propertyTestSessionInfo(new TestSomeSessionRequest(login));
        Assertions.assertThrows(LoginBlacklistedException.class, executable);
    }

}