package ru.clevertec.parser.service.injectors;

import lombok.SneakyThrows;
import ru.clevertec.parser.service.ClassCast;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class PrimitiveFieldInjector implements FieldInjector {
    @Override
    public boolean isSupportedType(Class<?> type) {
        return type.isPrimitive();
    }

    @Override
    @SneakyThrows
    public void injectField(Object instance, Field field, Object valueForField) {
        Type genericType = field.getGenericType();
        Class<?> clazz = ClassCast.convertPrimitiveToWrapperClass(genericType);
        field.set(instance, ClassCast.castTo(clazz, valueForField));
    }
}
