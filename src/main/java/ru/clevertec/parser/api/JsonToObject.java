package ru.clevertec.parser.api;

public interface JsonToObject {
    <T> T parseToObject(String jsonString, Class<T> tClass);
}
