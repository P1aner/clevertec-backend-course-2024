package com.clevertec.videohosting.controller;

import com.clevertec.videohosting.service.SubscribeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/channels")
@RequiredArgsConstructor
@Tag(name = "subscription", description = "Subscription operation")
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping("/{channelId}/subscribe/{userId}")
    @Operation(summary = "Create subscription", description = "Create a user subscription to the channel")
    @ApiResponse(responseCode = "201", description = "Successful operation")
    public ResponseEntity<Void> subscribeUser(@PathVariable
                                              @Parameter(
                                                      description = "Id of channel",
                                                      example = "1",
                                                      required = true) Long channelId,
                                              @PathVariable
                                              @Parameter(
                                                      description = "Id of user",
                                                      example = "1",
                                                      required = true) Long userId) {
        subscribeService.subscribeUser(channelId, userId);
        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }

    @DeleteMapping("/{channelId}/unsubscribe/{userId}")
    @Operation(summary = "Create subscription", description = "Create a user subscription to the channel")
    @ApiResponse(responseCode = "204", description = "Successful operation")
    public ResponseEntity<Void> unsubscribeUser(@PathVariable
                                                @Parameter(
                                                        description = "Id of channel",
                                                        example = "1",
                                                        required = true) Long channelId,
                                                @PathVariable
                                                @Parameter(
                                                        description = "Id of user",
                                                        example = "1",
                                                        required = true) Long userId) {
        subscribeService.unsubscribeUser(channelId, userId);
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }
}
