package ru.clevertec.parser.service;

import lombok.SneakyThrows;
import ru.clevertec.parser.annotation.JsonField;
import ru.clevertec.parser.service.injectors.ArrayFieldInjector;
import ru.clevertec.parser.service.injectors.CollectionFieldInjector;
import ru.clevertec.parser.service.injectors.FieldInjector;
import ru.clevertec.parser.service.injectors.MapFieldInjector;
import ru.clevertec.parser.service.injectors.PrimitiveFieldInjector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapToObjectParser {

    private static final List<FieldInjector> FIELD_INJECTOR_LIST = new ArrayList<>();

    static {
        FIELD_INJECTOR_LIST.add(new PrimitiveFieldInjector());
        FIELD_INJECTOR_LIST.add(new CollectionFieldInjector());
        FIELD_INJECTOR_LIST.add(new MapFieldInjector());
        FIELD_INJECTOR_LIST.add(new ArrayFieldInjector());
    }

    //todo паттерн стратегия
    @SneakyThrows
    public static <T> T parseMapToObject(Map<String, Object> stringObjectMap, Class<T> tClass) {
        T instance = tClass.getDeclaredConstructor().newInstance();
        Field[] fields = tClass.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            JsonField annotation = field.getAnnotation(JsonField.class);
            if (annotation != null && !annotation.name().isEmpty()) {
                fieldName = annotation.name();
            }
            Object valueOfMap = stringObjectMap.get(fieldName);
            if (valueOfMap != null) {
                field.setAccessible(true);
                FIELD_INJECTOR_LIST.stream()
                        .filter(s -> s.isSupportedType(fieldType))
                        .findFirst()
                        .ifPresentOrElse(s -> s.injectField(instance, field, valueOfMap),
                                recursiveParsing(field, instance, valueOfMap));
                field.setAccessible(false);
            }
        });
        return instance;
    }

    private static <T> Runnable recursiveParsing(Field field, T instance, Object valueOfMap) {
        return () -> {
            try {
                field.set(instance, ClassCast.castTo((Class<?>) field.getGenericType(), valueOfMap));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
