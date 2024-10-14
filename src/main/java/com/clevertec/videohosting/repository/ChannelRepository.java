package com.clevertec.videohosting.repository;

import com.clevertec.videohosting.model.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Page<Channel> findAll(Specification<Channel> spec, Pageable pageable);
}
