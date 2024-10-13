package com.clevertec.videohosting.controller;

import com.clevertec.videohosting.dto.ChannelNameDto;
import com.clevertec.videohosting.dto.CreateAppUserDto;
import com.clevertec.videohosting.dto.UpdatedAppUserDto;
import com.clevertec.videohosting.service.AppUserService;
import lombok.RequiredArgsConstructor;
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
public class AppUserController {
    private final AppUserService appUserService;

    @PostMapping
    public ResponseEntity<CreateAppUserDto> createAppUser(@RequestBody CreateAppUserDto appUserDto) {
        return ResponseEntity.ok(appUserService.createAppUser(appUserDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatedAppUserDto> updateAppUser(@PathVariable Long id,
                                                           @RequestBody UpdatedAppUserDto updatedAppUserDto) {
        return ResponseEntity.ok(appUserService.updateAppUser(id, updatedAppUserDto));
    }

    @GetMapping("/{userId}/subscriptions")
    public ResponseEntity<List<ChannelNameDto>> getAllAppUserSubscriptions(@PathVariable Long userId) {
        return ResponseEntity.ok(appUserService.getAllUserSubscriptions(userId));
    }
}
