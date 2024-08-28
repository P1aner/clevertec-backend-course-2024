package ru.clevertec.parser.service;

import lombok.SneakyThrows;
import ru.clevertec.parser.annotation.JSONField;
import ru.clevertec.parser.api.JsonToMap;
import ru.clevertec.parser.api.JsonToObject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassParser implements JsonToObject {

    @Override
    public <T> T parseToObject(String jsonString, Class<T> tClass) {
        JsonToMap jsonToMap = new JSONValue();
        Map<String, Object> stringObjectMap = jsonToMap.parseToMap(jsonString);
        return parseMapToObject(stringObjectMap, tClass);
    }

    @SneakyThrows
    private <T> T parseMapToObject(Map<String, Object> stringObjectMap, Class<T> tClass) {
        T instance = null;
        instance = tClass.newInstance();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String fieldName = field.getName();
            JSONField annotation = field.getAnnotation(JSONField.class);
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

    private Object[] getArray(Class<?> componentType, int arraySize, List listOfObjects) {
        Object[] array = EmptyConteinerGenerator.generateArray(componentType, arraySize);
        for (int i = 0; i < arraySize; i++) {
            array[i] = castTo(componentType, listOfObjects.get(i));
        }
        return array;
    }

    @SneakyThrows
    private void setPrimitiveValue(Object t, Field field, Object value) {
        String type = field.getGenericType().toString();
        Class clazz = convertPrimitiveToClass(type);
        field.set(t, castTo(clazz, value));
    }

    @SneakyThrows
    private void setArrayValue(Object instance, Field field, Object value) {
        List listOfObjects = (List) value;
        int arraySize = listOfObjects.size();
        Class<?> typeComponentType = field.getType().getComponentType();
        if (typeComponentType.isPrimitive()) {
            String typePrimitive = typeComponentType.toString();
            Object[] objects = getArray(convertPrimitiveToClass(typePrimitive), arraySize, listOfObjects);
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
                    int[] array = new int[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (int) objects[i];
                    }
                    field.set(instance, array);
                }
                case "long" -> {
                    long[] array = new long[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (long) objects[i];
                    }
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
                    double[] array = new double[arraySize];
                    for (int i = 0; i < arraySize; i++) {
                        array[i] = (double) objects[i];
                    }
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
            }
        } else {
            Object[] array = getArray(typeComponentType, arraySize, listOfObjects);
            field.set(instance, array);
        }
    }

    @SneakyThrows
    private void setCollectionValue(Object instance, Field field, Object value) {
        List objectListValue = (List) value;
        Class clazz;
        try {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            Type[] typeArguments = genericType.getActualTypeArguments();
            clazz = (Class) typeArguments[0];
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Collection collection = EmptyConteinerGenerator.generateCollection(field.getType());
        objectListValue.forEach(s -> collection.add(castTo(clazz, s)));
        field.set(instance, collection);
    }

    @SneakyThrows
    private void setMapValue(Object instance, Field field, Object value) {
        Map valueOfMap = (Map) value;
        Class classKey;
        Class classValue;
        Type[] typeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
        classKey = (Class) typeArguments[0];
        classValue = (Class) typeArguments[1];
        Map map = EmptyConteinerGenerator.generateMap(field.getType());
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
            default -> throw new RuntimeException("primitive not found");
        };
    }

    private <T> T castTo(Class tClass, Object value) {
        Object val;
        if (Byte.class.equals(tClass)) val = Byte.parseByte((String) value);
        else if (Short.class.equals(tClass)) val = Short.parseShort((String) value);
        else if (Integer.class.equals(tClass)) val = Integer.parseInt((String) value);
        else if (Long.class.equals(tClass)) val = Long.parseLong((String) value);
        else if (Float.class.equals(tClass)) val = Float.parseFloat((String) value);
        else if (Double.class.equals(tClass)) val = Double.parseDouble((String) value);
        else if (Boolean.class.equals(tClass)) val = Boolean.parseBoolean((String) value);
        else if (Character.class.equals(tClass)) val = Character.valueOf(((String) value).charAt(0));
        else if (String.class.equals(tClass)) val = String.valueOf(value);
        else val = parseMapToObject((Map) value, tClass);
        return (T) val;
    }
}