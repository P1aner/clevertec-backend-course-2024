package ru.clevertec.starter.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.starter.bpp.SessionInjectorBeanPostProcessor;
import ru.clevertec.starter.service.SessionListener;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({StarterConfig.class, BlackListPropertiesHandler.class})
public class SessionInjectorConfig {

    private final StarterConfig starterConfig;
    private final BlackListPropertiesHandler blackListPropertiesHandler;

    @Bean
    public SessionInjectorBeanPostProcessor sessionInjectorBeanPostProcessor() {
        Set<String> blackList = new HashSet<>(blackListPropertiesHandler.getBlackList());
        return new SessionInjectorBeanPostProcessor(new SessionListener(starterConfig), blackList);
    }
}
