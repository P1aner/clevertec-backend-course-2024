package ru.clevertec.test.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.clevertec.test.model.SomeSessionRequest;


@Component
@RequiredArgsConstructor
public class SessionTest {
    public static final String LOGIN = "login";
    private final Service service;

    @PostConstruct
    public void test() {
        service.sessionInfo(new SomeSessionRequest(LOGIN));
    }
}
