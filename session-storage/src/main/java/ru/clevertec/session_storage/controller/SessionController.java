package ru.clevertec.session_storage.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@Tag(name = "session", description = "Session operation")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/create")
    @Operation(summary = "Create session", description = "This POST method created session in the database.")
    @ApiResponse(responseCode = "201", description = "Successful operation. Object created.")
    public ResponseEntity<SessionDto> createSession(@RequestParam
                                                    @Parameter(
                                                            name = "login",
                                                            description = "User's login",
                                                            example = "username") String login) {
        return new ResponseEntity<>(sessionService.createSession(login), HttpStatus.CREATED);
    }

    @GetMapping("/current")
    @Operation(summary = "Show session", description = "Get current session.")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<SessionDto> getSessionByLogin(@RequestParam
                                                        @Parameter(
                                                                name = "login",
                                                                description = "User's login",
                                                                example = "username") String login) {
        return new ResponseEntity<>(sessionService.getSessionByLogin(login), HttpStatus.OK);
    }
}
