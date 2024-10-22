package ru.clevertec.starter.bpp.test_config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.clevertec.starter.bpp.SessionInjectorBeanPostProcessor;
import ru.clevertec.starter.bpp.test_model.TestStaticInitBlackListHandler;
import ru.clevertec.starter.bpp.test_service.TestService;
import ru.clevertec.starter.configuration.BlackListPropertiesHandler;
import ru.clevertec.starter.configuration.StarterConfig;
import ru.clevertec.starter.service.SessionListener;

import java.util.HashSet;
import java.util.Set;


@EnableConfigurationProperties({StarterConfig.class, BlackListPropertiesHandler.class})
@SpringBootConfiguration
public class TestConfig {

    public TestConfig(StarterConfig starterConfig, BlackListPropertiesHandler blackListPropertiesHandler) {
        this.starterConfig = starterConfig;
        this.blackListPropertiesHandler = blackListPropertiesHandler;
    }

    private final StarterConfig starterConfig;
    private final BlackListPropertiesHandler blackListPropertiesHandler;

    @Bean
    public SessionInjectorBeanPostProcessor sessionInjectorBeanPostProcessor() {
        Set<String> blackList = new HashSet<>(blackListPropertiesHandler.getBlackList());
        return new SessionInjectorBeanPostProcessor(new SessionListener(starterConfig), blackList);
    }

    @Bean
    public TestService getService() {
        return new TestService();
    }

    @Bean
    public TestStaticInitBlackListHandler getTestStaticInitBlackListHandler() {
        return new TestStaticInitBlackListHandler("static");
    }

}
