package ru.clevertec.test.model;

import org.springframework.context.annotation.Configuration;
import ru.clevertec.starter.configuration.BlackListHandler;

import java.util.List;

@Configuration
public class StaticInitBlackListHandler implements BlackListHandler {
    @Override
    public List<String> getBlackList() {
        return List.of("staticLogin");
    }
}
