package ru.clevertec.session_storage.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.stereotype.Component;
import ru.clevertec.session_storage.model.Session;

@Component
@RequiredArgsConstructor
public class MongoConfig {

    public static final String EXPIRE_AT = "expireAt";
    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void ensureIndexes() {
        IndexOperations indexOps = mongoTemplate.indexOps(Session.class);
        Index index = new Index().on(EXPIRE_AT, Sort.Direction.ASC);
        indexOps.ensureIndex(index);
    }
}
