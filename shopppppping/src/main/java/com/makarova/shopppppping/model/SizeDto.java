package com.makarova.shopppppping.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SizeDto {

    private String name;

    @JsonProperty("origName")
    private String originalName;

    private Integer rank;

    @JsonProperty("optionId")
    private Long optionId;

    private Integer wh;
    private Integer time1;
    private Integer time2;
    private Long dtype;
    private PriceDto price;

    @JsonProperty("saleConditions")
    private Long saleConditions;

    private String payload;

}
