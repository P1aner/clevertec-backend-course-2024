package ru.clevertec.parser.service.injectors;

import lombok.SneakyThrows;
import ru.clevertec.parser.service.ClassCast;
import ru.clevertec.parser.utils.EmptyContainerGenerator;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class CollectionFieldInjector implements FieldInjector {
    @Override
    public boolean isSupportedType(Class<?> fieldType) {
        return Collection.class.isAssignableFrom(fieldType);
    }

    @Override
    @SneakyThrows
    public void injectField(Object instance, Field field, Object object) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        Type[] typeArguments = genericType.getActualTypeArguments();
        Class<?> genericClass = (Class<?>) typeArguments[0];
        Collection collection = EmptyContainerGenerator.generateCollection(field.getType());
        ((List<?>) object).forEach(s -> collection.add(ClassCast.castTo(genericClass, s)));
        field.set(instance, collection);
    }
}
