package com.clevertec.videohosting.dto.mapper;

import com.clevertec.videohosting.dto.ChannelDto;
import com.clevertec.videohosting.dto.ChannelNameDto;
import com.clevertec.videohosting.dto.CreateChannelDto;
import com.clevertec.videohosting.dto.FilteredChannelDto;
import com.clevertec.videohosting.dto.UpdatedChannelDto;
import com.clevertec.videohosting.model.AppUser;
import com.clevertec.videohosting.model.Channel;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ChannelMapper {


    UpdatedChannelDto toUpdatedChannelDto(Channel channel);

    ChannelNameDto toChannelNameDto(Channel channel);

    List<ChannelNameDto> toChannelNameDtoList(Set<Channel> channels);

    @Mapping(source = "subscribers", target = "subscriberCount", qualifiedByName = "usersToCount")
    @Mapping(source = "author", target = "authorId", qualifiedByName = "userToId")
    ChannelDto toChannelDto(Channel channel);

    @Mapping(source = "subscribers", target = "subscriberCount", qualifiedByName = "usersToCount")
    FilteredChannelDto toFilteredChannelDto(Channel channel);

    List<FilteredChannelDto> toFilteredhannelDtoList(List<Channel> channels);

    void updateChannelFromDto(UpdatedChannelDto dto, @MappingTarget Channel channel);

    @Mapping(source = "authorId", target = "author", qualifiedByName = "idToUser")
    Channel toChannel(CreateChannelDto createChannelDTO);

    @Named("userToId")
    default Long userToId(AppUser appUser) {
        return appUser.getId();
    }

    @Named("idToUser")
    default AppUser idToUser(Long id) {
        return AppUser.builder()
                .id(id)
                .build();
    }

    @Named("usersToCount")
    default Long usersToCount(Set<AppUser> appUsers) {
        return (long) appUsers.size();
    }
}