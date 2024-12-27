package com.capstone.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc(OpenAPI) 설정.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Localhost Server"))
                .info(new Info()
                        .title("Capstone Project API")
                        .description("Capstone 프로젝트의 API 문서입니다.")
                        .version("1.0.0"));
    }

    /**
     * /api/users/**
     */
    @Bean
    public GroupedOpenApi userGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/users/**")
                .build();
    }

    /**
     * /api/goods/**
     */
    @Bean
    public GroupedOpenApi goodsGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("goods")
                .pathsToMatch("/api/goods/**")
                .build();
    }

    /**
     * /api/orders/**
     */
    @Bean
    public GroupedOpenApi ordersGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/api/orders/**")
                .build();
    }

    /**
     * /api/board/**
     */

    @Bean
    public GroupedOpenApi aiGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("AI Image")
                .pathsToMatch("/api/ai/**")
                .build();
    }

    @Bean
    public GroupedOpenApi diaryGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("Diary")
                .pathsToMatch("/api/diary/**")
                .build();
    }
}
