package ru.clevertec.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TopicManager implements Manager {
    private final Map<String, Topic> topics;

    private TopicManager(Map<String, Topic> topics) {
        this.topics = topics;
    }

    private static class TopicManagerSingletonHandler {
        private static final TopicManager instance = new TopicManager(new HashMap<>());
    }

    public static TopicManager getInstance() {
        return TopicManagerSingletonHandler.instance;
    }

    public Topic createTopic(String name, int maxConsumers) {
        return topics.computeIfAbsent(name, k -> new Topic(name, maxConsumers));
    }

    public Optional<Topic> getTopic(String name) {
        return Optional.ofNullable(topics.get(name));
    }
}
