package ru.clevertec.parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassParser {

    public static <T> T parse(Map<String, Object> stringObjectMap, Class<T> tClass) throws
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        T instance = tClass.newInstance();
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

    private static Object[] getArray(Class<?> componentType, int arraySize, List listOfObjects) throws
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Object[] array = EmptyObjectGenerator.generateArray(componentType, arraySize);
        for (int i = 0; i < arraySize; i++) {
            array[i] = castTo(componentType, listOfObjects.get(i));
        }
        return array;
    }


    private static void setPrimitiveValue(Object t, Field field, Object value) throws
            IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        String type = field.getGenericType().toString();
        Class clazz = convertPrimitiveToClass(type);
        field.set(t, castTo(clazz, value));
    }

    private static void setArrayValue(Object instance, Field field, Object valueOfMap) throws
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List listOfObjects = (List) valueOfMap;
        int arraySize = listOfObjects.size();
        Class<?> fieldType = field.getType();
        Class<?> typeComponentType = fieldType.getComponentType();
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

    private static void setCollectionValue(Object instance, Field field, Object valueOfMap) throws
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List object1 = (ArrayList) valueOfMap;
        Class<?> fieldType = field.getType();
        int size = object1.size();
        Class clazz;
        try {
            ParameterizedType genericType2 = (ParameterizedType) field.getGenericType();
            Type[] typeArguments = genericType2.getActualTypeArguments();
            clazz = (Class) typeArguments[0];
        } catch (Exception e) {
            clazz = Object.class;//todo exe
        }
        Collection collection = EmptyObjectGenerator.generateCollection(fieldType);
        for (int i = 0; i < size; i++) {
            collection.add(castTo(clazz, object1.get(i)));
        }
        field.set(instance, collection);
    }

    private static void setMapValue(Object instance, Field field, Object valueOfMap) throws
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map object1 = (Map) valueOfMap;
        Class<?> fieldType = field.getType();
        int size = object1.size();
        Class classKey;
        Class classValue;
        try {
            Type[] typeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            classKey = (Class) typeArguments[0];
            classValue = (Class) typeArguments[1];
        } catch (Exception e) {
            classKey = Object.class;
            classValue = Object.class;
        }
        Map map = (Map) EmptyObjectGenerator.generateMap(fieldType);
        Set set = object1.keySet();
        Object[] array = set.toArray();
        for (int i = 0; i < size; i++) {
            map.put(castTo(classKey, array[i]), castTo(classValue, object1.get(array[i])));
        }
        field.set(instance, map);
    }


    private static Class convertPrimitiveToClass(String stringGenericType) {
        return switch (stringGenericType) {
            case "byte" -> Byte.class;
            case "short" -> Short.class;
            case "int" -> Integer.class;
            case "long" -> Long.class;
            case "float" -> Float.class;
            case "double" -> Double.class;
            case "boolean" -> Boolean.class;
            case "char" -> Character.class;
            default -> null;// throw e
        };
    }

    private static <T> T castTo(Class tClass, Object value) throws
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Object val;
        if (Byte.class.equals(tClass)) val = Byte.parseByte((String) value);
        else if (Short.class.equals(tClass)) val = Short.parseShort((String) value);
        else if (Integer.class.equals(tClass)) val = Integer.parseInt((String) value);
        else if (Long.class.equals(tClass)) val = Long.parseLong((String) value);
        else if (Float.class.equals(tClass)) val = Float.parseFloat((String) value);
        else if (Double.class.equals(tClass)) val = Double.parseDouble((String) value);
        else if (Boolean.class.equals(tClass)) val = Boolean.parseBoolean((String) value);
        else if (Character.class.equals(tClass)) val = Character.codePointAt((String) value, 0);
        else if (String.class.equals(tClass)) val = String.valueOf(value);
            //todo is array
        else val = parse((Map) value, tClass);

        return (T) val;
    }

}