package ru.clevertec.parser.api;

import java.util.Map;

public interface JsonToMap {
    Map<String, Object> parseToMap(String jsonString);
}
