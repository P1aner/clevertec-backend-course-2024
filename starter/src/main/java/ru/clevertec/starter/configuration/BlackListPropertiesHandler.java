package ru.clevertec.starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "starter")
public record BlackListPropertiesHandler(
        List<String> blackList
) implements BlackListHandler {
    @Override
    public List<String> getBlackList() {
        return new ArrayList<>(blackList);
    }
}
