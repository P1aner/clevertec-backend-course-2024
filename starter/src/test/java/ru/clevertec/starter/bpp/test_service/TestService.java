package ru.clevertec.starter.bpp.test_service;


import ru.clevertec.starter.annotation.SessionInjector;
import ru.clevertec.starter.bpp.test_model.TestSomeSessionRequest;
import ru.clevertec.starter.bpp.test_model.TestStaticInitBlackListHandler;


public class TestService {

    @SessionInjector(blackList = {TestStaticInitBlackListHandler.class})
    public String staticTestsSessionInfo(TestSomeSessionRequest testSomeSessionRequest) {
        return testSomeSessionRequest.getSession().getLogin();
    }

    @SessionInjector
    public String propertyTestSessionInfo(TestSomeSessionRequest testSomeSessionRequest) {
        return testSomeSessionRequest.getSession().getLogin();
    }

}
