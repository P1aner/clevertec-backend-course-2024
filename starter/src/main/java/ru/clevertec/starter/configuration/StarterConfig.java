package ru.clevertec.starter.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "starter.uli-config")
public record StarterConfig(
        String protocol,
        String domain,
        String port,
        String pathToObtainSession,
        String pathToCreateSession,
        String loginParameter
) {
}
