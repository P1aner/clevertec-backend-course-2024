package ru.clevertec.parser.service.command;

import ru.clevertec.parser.service.MapperFacade;
import ru.clevertec.parser.service.MapperFacadeProxy;
import ru.clevertec.parser.service.observers.Subject;

public class JsonSendCommand implements Command {
    private final Subject receiver;
    private final String jsonString;
    private final Class<?> tClass;

    public JsonSendCommand(Subject receiver, String jsonString, Class<?> tClass) {
        this.receiver = receiver;
        this.jsonString = jsonString;
        this.tClass = tClass;
    }

    @Override
    public <T> T execute() {//todo паттерн комманда
        receiver.notifyObservers(jsonString);
        return (T) tClass.cast(new MapperFacadeProxy(new MapperFacade()).parseToObject(jsonString, tClass));
    }
}