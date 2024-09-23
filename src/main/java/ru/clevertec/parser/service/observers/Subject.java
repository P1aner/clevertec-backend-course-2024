package ru.clevertec.parser.service.observers;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    //todo Паттерн слушатель. Наблюдает за изменениями класса и текстом, при котором приложение выполняется. Выводит этот текст в консоль или файл (не реализовывал, стоит заглушка).
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}