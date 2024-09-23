package ru.clevertec.parser.service.api;

public interface JsonToObject {
    <T> T parseToObject(String jsonString, Class<T> tClass);
}
