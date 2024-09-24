package ru.clevertec.producer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.clevertec.manager.Manager;
import ru.clevertec.manager.Topic;

@RequiredArgsConstructor
public class Producer {
    private final Manager manager;
    private final String topicName;

    @SneakyThrows
    public void createTopicContent(int maxConsumers, int countOfMessages) {
        Topic topic = manager.createTopic(topicName, maxConsumers);
        for (int i = 0; i < countOfMessages; i++) {
            topic.publishMessage(String.format("%s. Message: %s", Thread.currentThread().getName(), i));
        }
    }
}