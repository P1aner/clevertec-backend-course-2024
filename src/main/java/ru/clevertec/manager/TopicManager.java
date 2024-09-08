package ru.clevertec.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum TopicManager implements Manager {

    INSTANCE {
        @Override
        public Topic createTopic(String name, int maxConsumers) {
            return super.createTopic(name, maxConsumers);
        }

        @Override
        public Optional<Topic> getTopic(String name) {
            return super.getTopic(name);
        }
    };

    private final Map<String, Topic> topics;

    TopicManager() {
        topics = new HashMap<>();
    }

    public Topic createTopic(String name, int maxConsumers) {
        Topic topic = topics.get(name);
        if (topic == null) {
            topics.put(name, new Topic(name, maxConsumers));
        }
        return topics.get(name);
    }

    public Optional<Topic> getTopic(String name) {
        return Optional.ofNullable(topics.get(name));
    }
}
