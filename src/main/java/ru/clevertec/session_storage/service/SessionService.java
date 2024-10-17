package ru.clevertec.session_storage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.session_storage.exception.ResourceNotFoundException;
import ru.clevertec.session_storage.model.Session;
import ru.clevertec.session_storage.repository.SessionRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    public Session createSession(String login) {
        return sessionRepository.save(Session.builder()
                .login(login)
                .openTime(LocalDateTime.now())
                .build());
    }

    public Session getSessionByLogin(String login) {
        return sessionRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException("Session not found"));
    }

    public void cleanupSessions() {
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
      //todo  sessionRepository.deleteAll(sessionRepository.findByStartTimeBefore(endOfDay));
    }
}
