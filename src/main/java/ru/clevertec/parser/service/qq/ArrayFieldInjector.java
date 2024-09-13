package ru.clevertec.parser.service.qq;

import lombok.SneakyThrows;
import ru.clevertec.parser.annotation.JsonField;
import ru.clevertec.parser.exception.ClassForParseNotFoundException;
import ru.clevertec.parser.service.EmptyContainerGenerator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ArrayFieldInjector implements FieldInjector {
    @Override
    public boolean isSupportedType(Class<?> type) {
        return type.isArray();
    }

    @Override
    @SneakyThrows
    public void injectField(Object instance, Field field, Object valueForField) {
        List listOfObjects = (List) valueForField;
        int arraySize = listOfObjects.size();
        Class<?> typeComponentType = field.getType().getComponentType();
        String typePrimitive = typeComponentType.toString();
        Object[] objects = getArrayWithObjects(convertPrimitiveToClass(typePrimitive), listOfObjects);
        Object o = Array.newInstance(typeComponentType, arraySize);
        for (int i = 0; i < arraySize; i++) {
            Array.set(o, i, objects[i]);
        }
        field.set(instance, o);
    }

    private Object[] getArrayWithObjects(Class<?> componentType, List listOfObjects) {
        return listOfObjects.stream()
                .map(s -> castTo(componentType, s))
                .toArray();
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

                    new ArrayFieldInjector().injectField(instance, field, valueOfMap);

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

    @SneakyThrows
    private void setPrimitiveValue(Object instance, Field field, Object valueForField) {
        String type = field.getGenericType().toString();
        Class clazz = convertPrimitiveToClass(type);
        field.set(instance, castTo(clazz, valueForField));
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

    @SneakyThrows
    private void setArrayValue(Object instance, Field field, Object valueForField) {
        List listOfObjects = (List) valueForField;
        int arraySize = listOfObjects.size();
        Class<?> typeComponentType = field.getType().getComponentType();
        if (typeComponentType.isPrimitive()) {
            String typePrimitive = typeComponentType.toString();
            Object[] objects = getArrayWithObjects(convertPrimitiveToClass(typePrimitive), listOfObjects);
            Object o = Array.newInstance(typeComponentType, arraySize);
            for (int i = 0; i < arraySize; i++) {
                Array.set(o, i, objects[i]);
            }
            field.set(instance, o);
        }
    }
}