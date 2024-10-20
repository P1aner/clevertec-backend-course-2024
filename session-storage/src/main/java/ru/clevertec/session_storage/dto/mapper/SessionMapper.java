package ru.clevertec.session_storage.dto.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.session_storage.dto.SessionDto;
import ru.clevertec.session_storage.model.Session;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SessionMapper {
    SessionDto toSessionDto(Session session);

}