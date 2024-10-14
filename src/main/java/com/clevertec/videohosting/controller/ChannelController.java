package com.clevertec.videohosting.controller;

import com.clevertec.videohosting.dto.ChannelDto;
import com.clevertec.videohosting.dto.CreateChannelDto;
import com.clevertec.videohosting.dto.CreatedChannelDto;
import com.clevertec.videohosting.dto.FilteredChannelDto;
import com.clevertec.videohosting.dto.UpdatedChannelDto;
import com.clevertec.videohosting.model.enums.Language;
import com.clevertec.videohosting.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Create channel", description = "This POST method takes a json as input and creates an object in the database.")
    @ApiResponse(responseCode = "201", description = "Successful operation. Object created.")
    public ResponseEntity<CreatedChannelDto> createChannel(@RequestBody CreateChannelDto createChannelDTO) {
        return ResponseEntity.ok(channelService.createChannel(createChannelDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updated channel", description = "Update channel by channel id")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<UpdatedChannelDto> updateChannel(@PathVariable
                                                           @Parameter(
                                                                   description = "Id of channel",
                                                                   example = "1",
                                                                   required = true) Long id,
                                                           @RequestBody UpdatedChannelDto updatedChannelDto) {
        return ResponseEntity.ok(channelService.updateChannel(id, updatedChannelDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Show channel", description = "Get channel information by channel id")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<ChannelDto> getChannel(@PathVariable
                                                 @Parameter(
                                                         description = "Id of channel",
                                                         example = "1",
                                                         required = true) Long id) {
        return ResponseEntity.ok(channelService.getChannelDetails(id));
    }

    @GetMapping
    @Operation(summary = "Show channels", description = "Get list of channels with pagination, filter of name, language, category.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<List<FilteredChannelDto>> getAllChannels(@RequestParam(required = false)
                                                                   @Parameter(
                                                                           name = "channelName",
                                                                           description = "Channel name",
                                                                           example = "Travel") String channelName,
                                                                   @RequestParam(required = false)
                                                                   @Parameter(
                                                                           name = "channelLanguage",
                                                                           description = "Channel language",
                                                                           example = "RUSSIAN") String channelLanguage,
                                                                   @RequestParam(required = false)
                                                                   @Parameter(
                                                                           name = "channelCategory",
                                                                           description = "Channel category",
                                                                           example = "animal") String channelCategory,
                                                                   Pageable pageable) {
        Language language = null;
        if (channelLanguage != null) language = Language.valueOf(channelLanguage);
        List<FilteredChannelDto> filteredChannels = channelService.getFilteredChannels(channelName, language, channelCategory, pageable);
        return ResponseEntity.ok(filteredChannels);
    }
}