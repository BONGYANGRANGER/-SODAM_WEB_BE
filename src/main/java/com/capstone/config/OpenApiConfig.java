package com.capstone.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Capstone API")
                        .description("API documentation for the Capstone project")
                        .version("1.0"));
    }

    @Bean
    public GroupedOpenApi userGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("users")
                .pathsToMatch("/api/users/**")
                .build();
    }

    @Bean
    public GroupedOpenApi goodsGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("goods")
                .pathsToMatch("/api/goods/**")
                .build();
    }

    @Bean
    public GroupedOpenApi ordersGroupOpenApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/api/orders/**")
                .build();
    }
}
