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

    /**
     * OpenAPI 기본 정보 및 서버 설정
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Localhost Server"))
                .info(new Info()
                        .title("Capstone Project API")
                        .description("Capstone 프로젝트의 API 문서입니다.")
                        .version("2.0.0"));
    }

    /**
     * /api/users/** 경로 그룹 설정
     */
    @Bean
    public GroupedOpenApi userGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/users/**")
                .build();
    }

    /**
     * /api/goods/** 경로 그룹 설정
     */
    @Bean
    public GroupedOpenApi goodsGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("goods")
                .pathsToMatch("/api/goods/**")
                .build();
    }

    /**
     * /api/orders/** 경로 그룹 설정
     */
    @Bean
    public GroupedOpenApi ordersGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/api/orders/**")
                .build();
    }

    /**
     * /api/ai/** 경로 그룹 설정
     */
    @Bean
    public GroupedOpenApi aiGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("AI Image")
                .pathsToMatch("/api/ai/**")
                .build();
    }

    /**
     * /api/diary/** 경로 그룹 설정
     */
    @Bean
    public GroupedOpenApi diaryGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("Diary")
                .pathsToMatch("/api/diary/**")
                .build();
    }

    /**
     * /api/cart/** 경로 그룹 설정
     */
    @Bean
    public GroupedOpenApi cartGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("cart")
                .pathsToMatch("/api/cart/**")
                .build();
    }

    /**
     * /api/wishlists/** 경로 그룹 설정
     */
    @Bean
    public GroupedOpenApi wishlistGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("wishlists")
                .pathsToMatch("/api/wishlists/**")
                .build();
    }
}
