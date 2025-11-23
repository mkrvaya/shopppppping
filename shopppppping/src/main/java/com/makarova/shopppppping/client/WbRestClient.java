package com.makarova.shopppppping.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makarova.shopppppping.model.CatalogResponseDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static com.makarova.shopppppping.config.rest.WbClientConfiguration.WB_CLIENT_BEAN_NAME;

@Slf4j
@Component
public class WbRestClient {

    public static final String WB_METHOD = "https://u-search.wb.ru/exactmatch/ru/common/v18/search";
    private final RestTemplate wbRestTemplate;
    private final ObjectMapper objectMapper;

    public WbRestClient(@Qualifier(WB_CLIENT_BEAN_NAME) RestTemplate restTemplate,
                        ObjectMapper objectMapper) {
        this.wbRestTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public CatalogResponseDto getProductsByFilter(String filter, Long page) {
        log.info("Search product {}", filter);

        String uri = UriComponentsBuilder.fromUriString(WB_METHOD)
                .queryParam("ab_testing", "{ab_testing}")
                .queryParam("appType", "{appType}")
                .queryParam("curr", "{curr}")
                .queryParam("hide_dtype", "{hide_dtype}")
                .queryParam("inheritFilters", "{inheritFilters}")
                .queryParam("dest", "{dest}")
                .queryParam("lang", "{lang}")
                .queryParam("page", "{page}")
                .queryParam("query", "{query}")
                .queryParam("resultset", "{resultset}")
                .queryParam("sort", "{sort}")
                .queryParam("spp", "{spp}")
                .queryParam("suppressSpellcheck", "{suppressSpellcheck}")
                .encode()
                .toUriString();

        log.info(uri);
        Map<String, String> map = new HashMap<>() {};
        map.put("ab_testing", "false");
        map.put("appType", "1");
        map.put("curr", "rub");
        map.put("hide_dtype", "11");
        map.put("dest", "-1185367");
        map.put("inheritFilters", "false");
        map.put("lang", "ru");
        map.put("page", String.valueOf(page));
        map.put("query", filter);
        map.put("resultset", "catalog");
        map.put("sort", "popular");
        map.put("spp", "30");
        map.put("suppressSpellcheck", "false");


        ResponseEntity<String> response =
                wbRestTemplate.exchange(uri, HttpMethod.GET,
                        null, new ParameterizedTypeReference<>() {},
                        map);

        log.info(response.getStatusCode().toString());
        log.debug(response.getBody());
        if (response.getStatusCode().is2xxSuccessful()) {
            CatalogResponseDto catalogResponseDto = objectMapper.readValue(response.getBody(), CatalogResponseDto.class);
            log.debug("catalog {}", catalogResponseDto);
            return catalogResponseDto;
        }
        throw new RuntimeException("Fail WB client get resource. Response code " + response.getStatusCode() + " . Response message " + response.getBody());
    }
}