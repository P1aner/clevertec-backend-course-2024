package ru.clevertec.session_storage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.session_storage.dto.SessionDto;
import ru.clevertec.session_storage.service.SessionService;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/create")
    public SessionDto createSession(@RequestParam String login) {
        return sessionService.createSession(login);
    }

    @GetMapping("/current")
    public SessionDto getSessionByLogin(@RequestParam String login) {
        return sessionService.getSessionByLogin(login);
    }
}