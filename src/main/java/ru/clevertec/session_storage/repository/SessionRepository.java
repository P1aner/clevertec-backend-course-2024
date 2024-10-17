package ru.clevertec.session_storage.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.clevertec.session_storage.model.Session;

import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {
    Optional<Session> findByLogin(String login);
}

