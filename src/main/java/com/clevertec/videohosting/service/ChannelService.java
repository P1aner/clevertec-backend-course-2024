package com.clevertec.videohosting.service;

import com.clevertec.videohosting.dto.ChannelDto;
import com.clevertec.videohosting.dto.CreateChannelDto;
import com.clevertec.videohosting.dto.FilteredChannelDto;
import com.clevertec.videohosting.dto.UpdatedChannelDto;
import com.clevertec.videohosting.dto.mapper.ChannelMapper;
import com.clevertec.videohosting.exception.ResourceNotFoundException;
import com.clevertec.videohosting.model.Channel;
import com.clevertec.videohosting.model.enums.Category;
import com.clevertec.videohosting.model.enums.Language;
import com.clevertec.videohosting.repository.ChannelRepository;
import com.clevertec.videohosting.repository.ChannelSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;

    public UpdatedChannelDto createChannel(CreateChannelDto createChannelDto) {
        Channel channel = channelMapper.toChannel(createChannelDto);
        Channel save = channelRepository.save(channel);
        return channelMapper.toUpdatedChannelDto(save);
    }

    public UpdatedChannelDto updateChannel(Long id, UpdatedChannelDto updatedChannelDto) {
        Channel updatedChannel = channelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Канал не найден"));
        channelMapper.updateChannelFromDto(updatedChannelDto, updatedChannel);
        Channel save = channelRepository.save(updatedChannel);
        return channelMapper.toUpdatedChannelDto(save);
    }


    public List<FilteredChannelDto> getFilteredChannels(String channelName, Language language, Category category, Pageable pageable) {
        Specification<Channel> spec = ChannelSpecification.filterBy(channelName, language, category);
        Page<Channel> all = channelRepository.findAll(spec, pageable);
        return channelMapper.toFilteredhannelDtoList(all.getContent());
    }

    public ChannelDto getChannelDetails(Long id) {
        Channel channel = channelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Channel not found"));
        return channelMapper.toChannelDto(channel);
    }
}