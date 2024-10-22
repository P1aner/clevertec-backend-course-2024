package ru.clevertec.test.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.clevertec.starter.annotation.SessionInjector;
import ru.clevertec.test.model.SomeSessionRequest;
import ru.clevertec.test.model.StaticInitBlackListHandler;

@Slf4j
@Component
@NoArgsConstructor
public class Service {

    @SessionInjector(blackList = {StaticInitBlackListHandler.class})
    public void sessionInfo(SomeSessionRequest someSessionRequest) {
        log.info("Required login: {}", someSessionRequest.getLogin());
        if (someSessionRequest.getSession() != null) {
            log.info("Response login: {}", someSessionRequest.getSession().getLogin());
        }
    }
}
