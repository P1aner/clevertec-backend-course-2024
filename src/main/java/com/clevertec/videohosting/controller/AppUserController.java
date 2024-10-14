package com.clevertec.videohosting.controller;

import com.clevertec.videohosting.dto.ChannelNameDto;
import com.clevertec.videohosting.dto.CreateAppUserDto;
import com.clevertec.videohosting.dto.UpdatedAppUserDto;
import com.clevertec.videohosting.service.AppUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "user", description = "User operation")
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping
    @Operation(summary = "Create user", description = "This POST method takes a json as input and creates an object in the database")
    @ApiResponse(responseCode = "201", description = "Successful operation. Object created.")
    public ResponseEntity<CreateAppUserDto> createAppUser(@RequestBody CreateAppUserDto appUserDto) {
        return new ResponseEntity<>(appUserService.createAppUser(appUserDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updated user information", description = "Update user object in JSON format by name. userName will not be changed")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<UpdatedAppUserDto> updateAppUser(@PathVariable
                                                           @Parameter(
                                                                   description = "Id of user",
                                                                   example = "1",
                                                                   required = true) Long id,
                                                           @RequestBody UpdatedAppUserDto updatedAppUserDto) {
        return ResponseEntity.ok(appUserService.updateAppUser(id, updatedAppUserDto));
    }

    @GetMapping("/{userId}/subscriptions")
    @Operation(summary = "User's subscription", description = "Get list of user subscriptions (without pagination, only channelName)")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<List<ChannelNameDto>> getAllAppUserSubscriptions(@PathVariable
                                                                           @Parameter(
                                                                                   description = "Id of user",
                                                                                   example = "1",
                                                                                   required = true) Long userId) {
        return ResponseEntity.ok(appUserService.getAllUserSubscriptions(userId));
    }
}
