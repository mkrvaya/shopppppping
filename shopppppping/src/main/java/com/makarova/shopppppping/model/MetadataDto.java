package com.makarova.shopppppping.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MetadataDto {
    @JsonProperty("catalog_type")
    private String catalogType;

    @JsonProperty("catalog_value")
    private String catalogValue;

    private String normquery;

    @JsonProperty("search_result")
    private Object searchResult;

    private String name;
    private String rmi;
    private String title;
    private Integer rs;
    private String qv;
    private String snippet;
}
