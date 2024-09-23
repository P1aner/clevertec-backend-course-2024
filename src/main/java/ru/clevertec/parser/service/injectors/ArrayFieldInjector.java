package ru.clevertec.parser.service.injectors;

import lombok.SneakyThrows;
import ru.clevertec.parser.service.ClassCast;
import ru.clevertec.parser.utils.EmptyContainerGenerator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;


public class ArrayFieldInjector implements FieldInjector {

    @Override
    public boolean isSupportedType(Class<?> type) {
        return type.isArray();
    }

    @Override
    @SneakyThrows
    public void injectField(Object instance, Field field, Object valueForField) {
        List<?> listOfObjects = (List<?>) valueForField;
        int arraySize = listOfObjects.size();
        Class<?> typeComponentType = field.getType().getComponentType();
        Object[] arrayWithObjects = getArrayWithObjects(ClassCast.convertPrimitiveToWrapperClass(typeComponentType), listOfObjects);
        Object generatedEmptyArray = EmptyContainerGenerator.generateArray(typeComponentType, arraySize);
        for (int i = 0; i < arraySize; i++) {
            Array.set(generatedEmptyArray, i, arrayWithObjects[i]);
        }
        field.set(instance, generatedEmptyArray);
    }

    private Object[] getArrayWithObjects(Class<?> componentType, List<?> listOfObjects) {
        return listOfObjects.stream()
                .map(s -> ClassCast.castTo(componentType, s))
                .toArray();
    }
}