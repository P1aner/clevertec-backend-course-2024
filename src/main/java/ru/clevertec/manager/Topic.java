package ru.clevertec.manager;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class Topic {

    private final String name;
    private final List<String> messages;
    private final ReentrantLock lock;
    private final Condition condition;
    private final Semaphore semaphore;

    public Topic(String name, int maxConsumers) {
        this.name = name;
        this.messages = new ArrayList<>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
        this.semaphore = new Semaphore(maxConsumers);
    }

    @SneakyThrows
    public void publishMessage(String message) {
        lock.lock();
        try {
            messages.add(name + ": " + message);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @SneakyThrows
    public String consumeMessage(int offset) {
        semaphore.acquire();
        lock.lock();
        try {
            while (offset >= messages.size()) {
                condition.await();
            }
            return messages.get(offset);
        } finally {
            lock.unlock();
            semaphore.release();
        }
    }
}
