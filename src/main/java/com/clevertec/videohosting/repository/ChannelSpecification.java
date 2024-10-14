package com.clevertec.videohosting.repository;

import com.clevertec.videohosting.model.Category;
import com.clevertec.videohosting.model.Channel;
import com.clevertec.videohosting.model.enums.Language;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class ChannelSpecification {

    private ChannelSpecification() {
    }

    public static Specification<Channel> filterBy(String name, Language language, Category category) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }
            if (language != null) {
                predicates.add(builder.equal(root.get("language"), language));
            }
            if (category != null) {
                predicates.add(builder.equal(root.get("category"), category));
            }
            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
