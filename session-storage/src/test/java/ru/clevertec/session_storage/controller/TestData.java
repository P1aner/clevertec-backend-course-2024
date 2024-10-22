package ru.clevertec.session_storage.controller;

import ru.clevertec.session_storage.model.Session;

public class TestData {
    public static final String LOGIN1 = "test_user1";
    public static final String LOGIN2 = "test_user2";
    public static final String CREATE_URL = "http://localhost:%s/sessions/create?login=%s";
    public static final String GET_URL = "http://localhost:%s/sessions/current?login=%s";

    public static Session getSession1() {
        return Session.builder()
                .login(LOGIN1)
                .build();
    }

    public static Session getSession2() {
        return Session.builder()
                .login(LOGIN2)
                .build();
    }
}
