package ru.clevertec.parser.service.injectors;

import lombok.SneakyThrows;
import ru.clevertec.parser.service.ClassCast;
import ru.clevertec.parser.utils.EmptyContainerGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

public class MapFieldInjector implements FieldInjector {
    @Override
    public boolean isSupportedType(Class<?> fieldType) {
        return Map.class.isAssignableFrom(fieldType);
    }

    @Override
    @SneakyThrows
    public void injectField(Object instance, Field field, Object valueForField) {
        Map valueOfMap = (Map) valueForField;
        Type[] genericTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        Class<?> classKey = (Class) genericTypeArguments[0];
        Class<?> classValue = (Class) genericTypeArguments[1];
        Map generatedEmptyMap = EmptyContainerGenerator.generateMap(field.getType());
        Set keySet = valueOfMap.keySet();
        keySet.forEach(s -> {
            Object key = ClassCast.castTo(classKey, s);
            Object value = ClassCast.castTo(classValue, valueOfMap.get(s));
            generatedEmptyMap.put(key, value);
        });
        field.set(instance, generatedEmptyMap);
    }
}
