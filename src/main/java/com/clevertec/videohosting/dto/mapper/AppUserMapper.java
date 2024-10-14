package com.clevertec.videohosting.dto.mapper;

import com.clevertec.videohosting.dto.CreateAppUserDto;
import com.clevertec.videohosting.dto.UpdatedAppUserDto;
import com.clevertec.videohosting.model.AppUser;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppUserMapper {
    UpdatedAppUserDto toUpdatedAppUserDto(AppUser appUser);

    CreateAppUserDto toCreateAppUserDto(AppUser appUser);

    AppUser toAppUser(CreateAppUserDto createAppUserDto);

    void updateAppUserFromDto(UpdatedAppUserDto dto, @MappingTarget AppUser appUser);
}
