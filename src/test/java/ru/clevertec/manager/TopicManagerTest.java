package ru.clevertec.manager;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import ru.clevertec.consumer.Consumer;
import ru.clevertec.producer.Producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

class TopicManagerTest {

    @SneakyThrows
    @RepeatedTest(100)
    void ManagerTest() {
        TopicManager topicManager = TopicManager.INSTANCE;
        List<Thread> threads = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        List<CountDownLatch> latches = new ArrayList<>();

        int count = 30000;
        String topicName = "topicName";

        Thread thread = new Thread(() -> new Producer(topicManager, topicName).createTopicContent(100, count), "Producer1");
        thread.start();

        Optional<Topic> topic = topicManager.getTopic(topicName);
        while (topic.isEmpty()) {
            topic = topicManager.getTopic(topicName);
        }

        for (int i = 0; i < 1000; i++) {
            CountDownLatch latch = new CountDownLatch(count);
            latches.add(latch);
            Consumer consumer = new Consumer(topic.get(), latch);
            consumers.add(consumer);
            threads.add(new Thread(consumer, "Thread " + i));
        }

        threads.forEach(Thread::start);

        for (CountDownLatch latch : latches) {
            latch.await();
        }

        Integer reduce = consumers.stream()
                .map(e -> e.getConsumedMessages().size())
                .reduce(0, Integer::sum);

        Assertions.assertEquals(count * consumers.size(), reduce);
    }
}