package com.makarova.shopppppping.config.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfiguration {

    public static final String REST_CLIENT_TEMPLATE_BUILDER = "restClientTemplateBuilder";

    @Bean({REST_CLIENT_TEMPLATE_BUILDER})
    public RestTemplateBuilder restClientTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
