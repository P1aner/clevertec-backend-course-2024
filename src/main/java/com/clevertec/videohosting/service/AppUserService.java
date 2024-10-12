package com.clevertec.videohosting.service;

import com.clevertec.videohosting.dto.ChannelNameDto;
import com.clevertec.videohosting.dto.CreateAppUserDto;
import com.clevertec.videohosting.dto.UpdatedAppUserDto;
import com.clevertec.videohosting.dto.mapper.AppUserMapper;
import com.clevertec.videohosting.dto.mapper.ChannelMapper;
import com.clevertec.videohosting.exception.ResourceNotFoundException;
import com.clevertec.videohosting.model.AppUser;
import com.clevertec.videohosting.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final ChannelMapper channelMapper;

    public List<ChannelNameDto> getAllUserSubscriptions(Long userId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));
        return channelMapper.toChannelNameDtoList(user.getSubscriptions());
    }

    public CreateAppUserDto createAppUser(CreateAppUserDto createAppUserDto) {
        AppUser appUser = appUserMapper.toAppUser(createAppUserDto);
        AppUser save = appUserRepository.save(appUser);
        return appUserMapper.toCreateAppUserDto(save);
    }

    public UpdatedAppUserDto updateAppUser(Long id, UpdatedAppUserDto updatedAppUserDto) {
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Канал не найден"));//todo
        appUserMapper.updateAppUserFromDto(updatedAppUserDto, appUser);
        return appUserMapper.toUpdatedAppUserDto(appUser);
    }
}
