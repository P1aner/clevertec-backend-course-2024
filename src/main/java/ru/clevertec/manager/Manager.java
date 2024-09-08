package ru.clevertec.manager;

import java.util.Optional;

public interface Manager {

    Topic createTopic(String name, int maxConsumers);

    Optional<Topic> getTopic(String name);

}
