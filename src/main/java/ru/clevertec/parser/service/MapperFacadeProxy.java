package ru.clevertec.parser.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.parser.annotation.Log;
import ru.clevertec.parser.exception.MethodNotFoundException;
import ru.clevertec.parser.service.api.JsonToObject;

import java.lang.reflect.Method;
import java.util.Arrays;

@RequiredArgsConstructor
public class MapperFacadeProxy implements JsonToObject {
    public static final String STARTING_METHOD = "Starting method: ";
    public static final String FINISHED_METHOD = "Finished method: ";
    public static final String PARSE_TO_OBJECT_METHOD_NAME = "parseToObject";

    private final JsonToObject jsonToObject;

    //todo Паттерн прокси. Добавляет новый функционал по логированию выполнения методов. Решает проблему лишней ответственности для базового метода.
    @Override
    public <T> T parseToObject(String jsonString, Class<T> tClass) {
        return Arrays.stream(jsonToObject.getClass().getMethods())
                .filter(s -> s.getName().equals(PARSE_TO_OBJECT_METHOD_NAME))
                .findFirst()
                .map(method -> execMethod(jsonString, tClass, method))
                .orElseThrow(() -> new MethodNotFoundException("Method not found"));
    }

    private <T> T execMethod(String jsonString, Class<T> tClass, Method method) {
        T t;
        if (method.isAnnotationPresent(Log.class)) {
            System.out.printf("%s%s.%s\n", STARTING_METHOD, jsonToObject.getClass().getName(), method.getName());
            t = jsonToObject.parseToObject(jsonString, tClass);
            System.out.printf("%s%s.%s\n", FINISHED_METHOD, jsonToObject.getClass().getName(), method.getName());
        } else {
            t = jsonToObject.parseToObject(jsonString, tClass);
        }
        return t;
    }
}
