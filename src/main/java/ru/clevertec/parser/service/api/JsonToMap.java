package ru.clevertec.parser.service.api;

import java.util.Map;

public interface JsonToMap {
    Map<String, Object> parseToMap(String jsonString);
}
