package com.clevertec.videohosting.service;

import com.clevertec.videohosting.dto.ChannelDto;
import com.clevertec.videohosting.dto.CreateChannelDto;
import com.clevertec.videohosting.dto.CreatedChannelDto;
import com.clevertec.videohosting.dto.FilteredChannelDto;
import com.clevertec.videohosting.dto.UpdatedChannelDto;
import com.clevertec.videohosting.dto.mapper.ChannelMapper;
import com.clevertec.videohosting.exception.ResourceNotFoundException;
import com.clevertec.videohosting.model.Category;
import com.clevertec.videohosting.model.Channel;
import com.clevertec.videohosting.model.enums.Language;
import com.clevertec.videohosting.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final ChannelMapper channelMapper;

    public CreatedChannelDto createChannel(CreateChannelDto createChannelDto) {
        Channel channel = channelMapper.toChannel(createChannelDto);
        Channel save = channelRepository.save(channel);
        return channelMapper.toCreatedChannelDto(save);
    }

    public UpdatedChannelDto updateChannel(Long id, UpdatedChannelDto updatedChannelDto) {
        Channel updatedChannel = channelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Channel not found"));
        channelMapper.updateChannelFromDto(updatedChannelDto, updatedChannel);
        if (updatedChannelDto.getCategoryName() != null) {
            Category category = categoryRepository.findByName(updatedChannelDto.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
            updatedChannel.setCategory(category);
        }
        Channel save = channelRepository.save(updatedChannel);
        return channelMapper.toUpdatedChannelDto(save);
    }

    public List<FilteredChannelDto> getFilteredChannels(String channelName, Language language, String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Specification<Channel> spec = ChannelSpecification.filterBy(channelName, language, category);
        Page<Channel> channelPage = channelRepository.findAll(spec, pageable);
        return channelMapper.toFilteredhannelDtoList(channelPage.getContent());
    }

    public ChannelDto getChannelDetails(Long id) {
        Channel channel = channelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Channel not found"));
        return channelMapper.toChannelDto(channel);
    }
}