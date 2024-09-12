package ru.clevertec.parser.service.qq;

import java.lang.reflect.Field;

public interface FieldInjector {

    boolean isSupportedType(Class<?> type);

    void injectField(Object t, Field field, Object object);
}
