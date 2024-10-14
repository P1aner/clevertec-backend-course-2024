package com.clevertec.videohosting.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springVideoHostingAPI() {
        return new OpenAPI()
                .info(new Info().title("VideoHosting")
                        .description("VideoHosting sample application REST API")
                        .version("v0.0.1"));
    }
}
