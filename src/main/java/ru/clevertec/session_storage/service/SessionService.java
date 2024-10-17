package ru.clevertec.session_storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.session_storage.dto.SessionDto;
import ru.clevertec.session_storage.dto.mapper.SessionMapper;
import ru.clevertec.session_storage.exception.ResourceNotFoundException;
import ru.clevertec.session_storage.model.Session;
import ru.clevertec.session_storage.repository.SessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    public SessionDto createSession(String login) {
        return sessionMapper.toSessionDto(sessionRepository.save(Session.builder()
                .login(login)
                .openAt(LocalDateTime.now())
                .expireAt(LocalDateTime.now())
                .build()));
    }

    public SessionDto getSessionByLogin(String login) {
        return sessionMapper.toSessionDto(sessionRepository.findByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found")));
    }
}
