package com.blackjack.blackjack_api.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI blackjackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blackjack API")
                        .description("API reactiva para el juego de Blackjack")
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio GitHub")
                        .url("https://github.com/acocinas/S501AdvancedSpringFrameworkWithWebFlux"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("blackjack")
                .pathsToMatch("/**")
                .build();
    }
}