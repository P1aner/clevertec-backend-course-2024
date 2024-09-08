package ru.clevertec.consumer;

import lombok.Getter;
import ru.clevertec.manager.Topic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class Consumer implements Runnable {

    private final Topic topic;
    private final CountDownLatch latch;
    private int offset;
    @Getter
    private final List<String> consumedMessages;

    public Consumer(Topic topic, CountDownLatch latch) {
        this.topic = topic;
        this.latch = latch;
        this.offset = 0;
        this.consumedMessages = new ArrayList<>();
    }

    @Override
    public void run() {
        while (latch.getCount() > 0) {
            String message = topic.consumeMessage(offset);
            consumedMessages.add(message);
            offset++;
            latch.countDown();
        }
    }
}
