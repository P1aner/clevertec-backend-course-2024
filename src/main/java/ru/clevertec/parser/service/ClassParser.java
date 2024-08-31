package ru.clevertec.parser.service;

import lombok.SneakyThrows;
import ru.clevertec.parser.annotation.JsonField;
import ru.clevertec.parser.api.JsonToMap;
import ru.clevertec.parser.api.JsonToObject;
import ru.clevertec.parser.exception.ClassForParseNotFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassParser implements JsonToObject {

    @Override
    public <T> T parseToObject(String jsonString, Class<T> tClass) {
        JsonToMap jsonToMap = new JsonValue();
        Map<String, Object> stringObjectMap = jsonToMap.parseToMap(jsonString);
        return parseMapToObject(stringObjectMap, tClass);
    }

    @SneakyThrows
    private <T> T parseMapToObject(Map<String, Object> stringObjectMap, Class<T> tClass) {
        T instance = tClass.newInstance();
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
                if (fieldType.isPrimitive()) {
                    setPrimitiveValue(instance, field, valueOfMap);
                } else if (fieldType.isArray()) {
                    setArrayValue(instance, field, valueOfMap);
                } else if (Collection.class.isAssignableFrom(fieldType)) {
                    setCollectionValue(instance, field, valueOfMap);
                } else if (Map.class.isAssignableFrom(fieldType)) {
                    setMapValue(instance, field, valueOfMap);
                } else {
                    field.set(instance, castTo((Class) field.getGenericType(), valueOfMap));
                }
                field.setAccessible(false);
            }
        }
        return instance;
    }

    private Object[] getArrayWithObjects(Class<?> componentType, List listOfObjects) {
        return listOfObjects.stream()
                .map(s -> castTo(componentType, s))
                .toArray();
    }

    @SneakyThrows
    private void setPrimitiveValue(Object instance, Field field, Object valueForField) {
        String type = field.getGenericType().toString();
        Class clazz = convertPrimitiveToClass(type);
        field.set(instance, castTo(clazz, valueForField));
    }

    @SneakyThrows
    private void setArrayValue(Object instance, Field field, Object valueForField) {
        List listOfObjects = (List) valueForField;
        int arraySize = listOfObjects.size();
        Class<?> typeComponentType = field.getType().getComponentType();
        if (typeComponentType.isPrimitive()) {
            String typePrimitive = typeComponentType.toString();
            Object[] objects = getArrayWithObjects(convertPrimitiveToClass(typePrimitive), listOfObjects);
            switch (typePrimitive) {
                case "byte" -> {
                    byte[] array = new byte[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (byte) objects[i];
                    }
                    field.set(instance, array);
                }
                case "short" -> {
                    short[] array = new short[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (short) objects[i];
                    }
                    field.set(instance, array);
                }
                case "int" -> {
                    int[] array = Arrays.stream(objects)
                            .mapToInt(i -> (int) i)
                            .toArray();
                    field.set(instance, array);
                }
                case "long" -> {
                    long[] array = Arrays.stream(objects)
                            .mapToLong(l -> (long) l)
                            .toArray();
                    field.set(instance, array);
                }
                case "float" -> {
                    float[] array = new float[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (float) objects[i];
                    }
                    field.set(instance, array);
                }
                case "double" -> {
                    double[] array = Arrays.stream(objects)
                            .mapToDouble(d -> (double) d)
                            .toArray();
                    field.set(instance, array);
                }
                case "boolean" -> {
                    boolean[] array = new boolean[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (boolean) objects[i];
                    }
                    field.set(instance, array);
                }
                case "char" -> {
                    char[] array = new char[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (char) objects[i];
                    }
                    field.set(instance, array);
                }
                default -> throw new ClassForParseNotFoundException("Unexpected value: " + typePrimitive);
            }
        } else {
            Object[] array = getArrayWithObjects(typeComponentType, listOfObjects);
            field.set(instance, array);
        }
    }

    @SneakyThrows
    private void setCollectionValue(Object instance, Field field, Object valueForField) {
        List objectListValue = (List) valueForField;
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] typeArguments = genericType.getActualTypeArguments();
        Class clazz = (Class) typeArguments[0];
        Collection collection = EmptyContainerGenerator.generateCollection(field.getType());
        objectListValue.forEach(s -> collection.add(castTo(clazz, s)));
        field.set(instance, collection);
    }

    @SneakyThrows
    private void setMapValue(Object instance, Field field, Object valueForField) {
        Map valueOfMap = (Map) valueForField;
        Type[] typeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        Class classKey = (Class) typeArguments[0];
        Class classValue = (Class) typeArguments[1];
        Map map = EmptyContainerGenerator.generateMap(field.getType());
        Set set = valueOfMap.keySet();
        set.forEach(s -> map.put(castTo(classKey, s), castTo(classValue, valueOfMap.get(s))));
        field.set(instance, map);
    }

    private Class convertPrimitiveToClass(String stringGenericType) {
        return switch (stringGenericType) {
            case "byte" -> Byte.class;
            case "short" -> Short.class;
            case "int" -> Integer.class;
            case "long" -> Long.class;
            case "float" -> Float.class;
            case "double" -> Double.class;
            case "boolean" -> Boolean.class;
            case "char" -> Character.class;
            default -> throw new ClassForParseNotFoundException("primitive not found");
        };
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