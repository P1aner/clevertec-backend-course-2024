package ru.clevertec.parser.service;

import lombok.RequiredArgsConstructor;
import ru.clevertec.parser.annotation.Log;
import ru.clevertec.parser.service.api.JsonToObject;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class MapperFacadeProxy implements JsonToObject {
    public static final String STARTING_METHOD = "Starting method: ";
    public static final String FINISHED_METHOD = "Finished method: ";
    public static final String PARSE_TO_OBJECT_METHOD_NAME = "parseToObject";

    private final JsonToObject jsonToObject;

    @Override
    public <T> T parseToObject(String jsonString, Class<T> tClass) {
        AtomicReference<T> t = new AtomicReference<>();
        Arrays.stream(jsonToObject.getClass().getMethods())
                .filter(s -> s.getName().equals(PARSE_TO_OBJECT_METHOD_NAME))
                .findFirst()
                .ifPresent(method -> {
                            if (method.isAnnotationPresent(Log.class)) {
                                System.out.printf("%s%s.%s\n", STARTING_METHOD, jsonToObject.getClass().getName(), method.getName());
                                t.set(jsonToObject.parseToObject(jsonString, tClass));
                                System.out.printf("%s%s.%s\n", FINISHED_METHOD, jsonToObject.getClass().getName(), method.getName());
                            } else {
                                t.set(jsonToObject.parseToObject(jsonString, tClass));
                            }
                        }
                );
        return t.get();
    }
}
