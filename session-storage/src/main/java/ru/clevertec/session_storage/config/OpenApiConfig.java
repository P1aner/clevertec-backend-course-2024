package ru.clevertec.session_storage.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String SESSION_MANAGER = "Session manager";
    public static final String DESCRIPTION = "Session manager sample application REST API";
    public static final String VERSION = "v0.0.1";

    @Bean
    public OpenAPI springVideoHostingAPI() {
        return new OpenAPI()
                .info(new Info().title(SESSION_MANAGER)
                        .description(DESCRIPTION)
                        .version(VERSION));
    }
}