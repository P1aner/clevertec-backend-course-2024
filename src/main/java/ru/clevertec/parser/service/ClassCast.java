package ru.clevertec.parser.service;

import lombok.SneakyThrows;
import ru.clevertec.parser.exception.ClassForParseNotFoundException;

import javax.print.attribute.UnmodifiableSetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ClassCast {

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TO_WRAPPER_MAP = new HashMap<>();
    private static final Map<Class<?>, Function<String, ?>> CAST_FUNCTION_MAP = new HashMap<>();

    static {
        PRIMITIVE_TO_WRAPPER_MAP.put(byte.class, Byte.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(short.class, Short.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(int.class, Integer.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(long.class, Long.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(float.class, Float.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(double.class, Double.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(boolean.class, Boolean.class);
        PRIMITIVE_TO_WRAPPER_MAP.put(char.class, Character.class);

        CAST_FUNCTION_MAP.put(Byte.class, Byte::parseByte);
        CAST_FUNCTION_MAP.put(Short.class, Short::parseShort);
        CAST_FUNCTION_MAP.put(Integer.class, Integer::parseInt);
        CAST_FUNCTION_MAP.put(Long.class, Long::parseLong);
        CAST_FUNCTION_MAP.put(Float.class, Float::parseFloat);
        CAST_FUNCTION_MAP.put(Double.class, Double::parseDouble);
        CAST_FUNCTION_MAP.put(Boolean.class, Boolean::parseBoolean);
        CAST_FUNCTION_MAP.put(Character.class, s -> s.charAt(0));
        CAST_FUNCTION_MAP.put(String.class, s -> s);
    }

    public static Class<?> convertPrimitiveToWrapperClass(Type genericType) {
        Class<?> wrapperClass = PRIMITIVE_TO_WRAPPER_MAP.get(genericType);
        if (wrapperClass != null) {
            return wrapperClass;
        } else {
            throw new ClassForParseNotFoundException("primitive not found");
        }
    }

    @SneakyThrows
    public static <T> T castTo(Class<T> tClass, Object valueForField) {
        Function<String, ?> caster = CAST_FUNCTION_MAP.get(tClass);
        if (caster != null) {
            return tClass.cast(caster.apply((String) valueForField));
        } else {
            return tClass.cast(MapToObjectParser.parseMapToObject((Map<String, Object>) valueForField, tClass));
        }
    }
}
