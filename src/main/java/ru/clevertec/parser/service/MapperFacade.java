package ru.clevertec.parser.service;

import ru.clevertec.parser.annotation.Log;
import ru.clevertec.parser.service.api.JsonToMap;
import ru.clevertec.parser.service.api.JsonToObject;

import java.util.Map;

public class MapperFacade implements JsonToObject {
    private final JsonToMap jsonToMap;

    public MapperFacade() {//todo Паттерн фасад. Дает простой и понятный доступ к функциональности программы.
        this.jsonToMap = new JsonValue();
    }

    @Log
    @Override
    public <T> T parseToObject(String jsonString, Class<T> tClass) {
        Map<String, Object> stringObjectMap = jsonToMap.parseToMap(jsonString);
        return MapToObjectParser.parseMapToObject(stringObjectMap, tClass);
    }
}