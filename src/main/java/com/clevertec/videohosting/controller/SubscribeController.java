package com.clevertec.videohosting.controller;

import com.clevertec.videohosting.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping("/{channelId}/subscribe/{userId}")
    public ResponseEntity<Void> subscribeUser(@PathVariable Long channelId,
                                              @PathVariable Long userId) {
        subscribeService.subscribeUser(channelId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{channelId}/unsubscribe/{userId}")
    public ResponseEntity<Void> unsubscribeUser(@PathVariable Long channelId,
                                                @PathVariable Long userId) {
        subscribeService.unsubscribeUser(channelId, userId);
        return ResponseEntity.ok().build();
    }
}
