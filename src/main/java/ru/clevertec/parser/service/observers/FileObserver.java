package ru.clevertec.parser.service.observers;

public class FileObserver implements Observer {

    @Override
    public void update(String message) {
        System.out.println("Received json: " + message);
    }
}

