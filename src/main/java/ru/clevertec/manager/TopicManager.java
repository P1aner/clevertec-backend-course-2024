package ru.clevertec.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TopicManager implements Manager {
    private static volatile TopicManager instance;
    private final Map<String, Topic> topics;

    private TopicManager(Map<String, Topic> topics) {
        this.topics = topics;
    }

    public static TopicManager getInstance() {
        TopicManager localInstance = instance;
        if (localInstance == null) {
            synchronized (TopicManager.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new TopicManager(new HashMap<>());
                }
            }
        }
        return localInstance;
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
