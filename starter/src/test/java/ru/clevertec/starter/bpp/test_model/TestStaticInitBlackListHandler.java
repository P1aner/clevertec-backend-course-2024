package ru.clevertec.starter.bpp.test_model;

import ru.clevertec.starter.configuration.BlackListHandler;

import java.util.List;

public class TestStaticInitBlackListHandler implements BlackListHandler {
    private final String blackListedTest;

    public TestStaticInitBlackListHandler(String blackListedTest) {
        this.blackListedTest = blackListedTest;
    }

    @Override
    public List<String> getBlackList() {
        return List.of(blackListedTest);
    }
}
