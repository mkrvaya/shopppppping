package com.makarova.shopppppping.config.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static com.makarova.shopppppping.config.rest.RestClientConfiguration.REST_CLIENT_TEMPLATE_BUILDER;
import static com.makarova.shopppppping.utils.RestUtils.trustAllClientHttpRequestFactory;

@Configuration
public class WbClientConfiguration {

    public static final String WB_CLIENT_BEAN_NAME = "wbClientRestTemplate";

    @Bean(WB_CLIENT_BEAN_NAME)
    public RestTemplate wbClientRestTemplate(@Qualifier(REST_CLIENT_TEMPLATE_BUILDER) RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setRequestFactory(trustAllClientHttpRequestFactory());
        return restTemplate;
    }
}
