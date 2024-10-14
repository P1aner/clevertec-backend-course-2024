package com.clevertec.videohosting.controller;

import com.clevertec.videohosting.dto.ChannelDto;
import com.clevertec.videohosting.dto.CreateChannelDto;
import com.clevertec.videohosting.dto.CreatedChannelDto;
import com.clevertec.videohosting.dto.FilteredChannelDto;
import com.clevertec.videohosting.dto.UpdatedChannelDto;
import com.clevertec.videohosting.model.Category;
import com.clevertec.videohosting.model.enums.Language;
import com.clevertec.videohosting.service.ChannelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
@Tag(name = "channel", description = "Channel operation")
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping
    public ResponseEntity<CreatedChannelDto> createChannel(@RequestBody CreateChannelDto createChannelDTO) {
        return ResponseEntity.ok(channelService.createChannel(createChannelDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedChannelDto> updateChannel(@PathVariable Long id,
                                                           @RequestBody UpdatedChannelDto updatedChannelDto) {
        return ResponseEntity.ok(channelService.updateChannel(id, updatedChannelDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelDto> getChannel(@PathVariable Long id) {
        return ResponseEntity.ok(channelService.getChannelDetails(id));
    }

    @GetMapping
    public ResponseEntity<List<FilteredChannelDto>> getAllChannels(@RequestParam(required = false) String channelName,
                                                                   @RequestParam(required = false) String channelLanguage,
                                                                   @RequestParam(required = false) String channelCategory,
                                                                   Pageable pageable) {
        Language language = null;
        if (channelLanguage != null) language = Language.valueOf(channelLanguage);
        List<FilteredChannelDto> filteredChannels = channelService.getFilteredChannels(channelName, language, channelCategory, pageable);
        return ResponseEntity.ok(filteredChannels);
    }
}