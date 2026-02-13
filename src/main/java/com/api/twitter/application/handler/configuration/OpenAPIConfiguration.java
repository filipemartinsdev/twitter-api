package com.api.twitter.application.handler.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Value("${spring.application.title}")
    private String title;

    @Value("${spring.application.version}")
    private String version;

    @Value("${spring.application.url}")
    private String url;

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url(url)
                        )
                )
                .info(new Info()
                        .title(title)
                        .version(version)
                );
    }
}
