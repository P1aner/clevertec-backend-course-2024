package ru.clevertec.starter.annotation;

import ru.clevertec.starter.configuration.BlackListHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionInjector {
    Class<? extends BlackListHandler>[] blackList() default {};
}
