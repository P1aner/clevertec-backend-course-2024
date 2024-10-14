package com.clevertec.videohosting.service;

import com.clevertec.videohosting.exception.ResourceNotFoundException;
import com.clevertec.videohosting.exception.SubscribeException;
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

    public void subscribeUser(Long channelId, Long appUserId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(() -> new ResourceNotFoundException("Channel not found"));
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new ResourceNotFoundException("AppUser not found"));
        if (channel.getSubscribers().contains(appUser))
            throw new SubscribeException("AppUser is already subscribe");
        channel.getSubscribers().add(appUser);
        channelRepository.save(channel);
    }

    public void unsubscribeUser(Long channelId, Long appUserId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(() -> new ResourceNotFoundException("Channel not found"));
        AppUser appUser = appUserRepository.findById(appUserId).orElseThrow(() -> new ResourceNotFoundException("AppUser not found"));
        boolean removeOperation = channel.getSubscribers().remove(appUser);
        if (!removeOperation)
            throw new SubscribeException("AppUser is already unsubscribe");
        channelRepository.save(channel);
    }
}
