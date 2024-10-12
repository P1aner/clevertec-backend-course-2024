package com.clevertec.videohosting.service;

import com.clevertec.videohosting.exception.ResourceNotFoundException;
import com.clevertec.videohosting.model.AppUser;
import com.clevertec.videohosting.model.Channel;
import com.clevertec.videohosting.repository.AppUserRepository;
import com.clevertec.videohosting.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final ChannelRepository channelRepository;
    private final AppUserRepository appUserRepository;

    public void subscribeUser(Long channelId, Long userId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(() -> new ResourceNotFoundException("Канал не найден"));
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        if (channel.getSubscribers().contains(user))
            throw new ResourceNotFoundException("Пользователь не подписан"); //todo
        channel.getSubscribers().add(user);
        channelRepository.save(channel);
    }

    public void unsubscribeUser(Long channelId, Long userId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(() -> new ResourceNotFoundException("Канал не найден"));
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        boolean remove = channel.getSubscribers().remove(user);
        if (!remove)
            throw new ResourceNotFoundException("Пользователь не подписан"); //todo
        channelRepository.save(channel);
    }
}
