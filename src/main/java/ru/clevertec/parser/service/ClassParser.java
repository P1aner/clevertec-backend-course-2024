package ru.clevertec.parser.service;

import lombok.SneakyThrows;
import ru.clevertec.parser.annotation.JsonField;
import ru.clevertec.parser.api.JsonToMap;
import ru.clevertec.parser.api.JsonToObject;
import ru.clevertec.parser.service.qq.ArrayFieldInjector;
import ru.clevertec.parser.service.qq.CollectionFieldInjector;
import ru.clevertec.parser.service.qq.FieldInjector;
import ru.clevertec.parser.service.qq.MapFieldInjector;
import ru.clevertec.parser.service.qq.PrimitiveFieldInjector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassParser implements JsonToObject {
    private List<FieldInjector> fieldInjectorList;

    public ClassParser() {
        this.fieldInjectorList = new ArrayList<>();
        this.fieldInjectorList.add(new PrimitiveFieldInjector());
        this.fieldInjectorList.add(new CollectionFieldInjector());
        this.fieldInjectorList.add(new MapFieldInjector());
        this.fieldInjectorList.add(new ArrayFieldInjector());
    }

    @Override
    public <T> T parseToObject(String jsonString, Class<T> tClass) {
        JsonToMap jsonToMap = new JsonValue();
        Map<String, Object> stringObjectMap = jsonToMap.parseToMap(jsonString);
        return parseMapToObject(stringObjectMap, tClass);
    }

    @SneakyThrows
    private <T> T parseMapToObject(Map<String, Object> stringObjectMap, Class<T> tClass) {
        T instance = tClass.getDeclaredConstructor().newInstance();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            JsonField annotation = field.getAnnotation(JsonField.class);
            if (annotation != null && !annotation.name().isEmpty()) {
                fieldName = annotation.name();
            }
            Object valueOfMap = stringObjectMap.get(fieldName);
            if (valueOfMap != null) {
                field.setAccessible(true);
                List<FieldInjector> list = fieldInjectorList.stream()
                        .filter(s -> s.isSupportedType(fieldType))
                        .toList();
                if (!list.isEmpty()) {
                    list.forEach(s -> s.injectField(instance, field, valueOfMap));
                } else {
                    field.set(instance, castTo((Class) field.getGenericType(), valueOfMap));
                }
                field.setAccessible(false);
            }
        }
        return instance;
    }


    private <T> T castTo(Class<T> tClass, Object valueForField) {
        Object castedValue;
        if (Byte.class.equals(tClass)) castedValue = Byte.parseByte((String) valueForField);
        else if (Short.class.equals(tClass)) castedValue = Short.parseShort((String) valueForField);
        else if (Integer.class.equals(tClass)) castedValue = Integer.parseInt((String) valueForField);
        else if (Long.class.equals(tClass)) castedValue = Long.parseLong((String) valueForField);
        else if (Float.class.equals(tClass)) castedValue = Float.parseFloat((String) valueForField);
        else if (Double.class.equals(tClass)) castedValue = Double.parseDouble((String) valueForField);
        else if (Boolean.class.equals(tClass)) castedValue = Boolean.parseBoolean((String) valueForField);
        else if (Character.class.equals(tClass)) castedValue = ((String) valueForField).charAt(0);
        else if (String.class.equals(tClass)) castedValue = String.valueOf(valueForField);
        else castedValue = parseMapToObject((Map) valueForField, tClass);
        return (T) castedValue;
    }
}