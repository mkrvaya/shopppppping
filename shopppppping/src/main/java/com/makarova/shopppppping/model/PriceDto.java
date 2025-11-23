package com.makarova.shopppppping.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {

    private Integer basic;
    private Long product;
    private Integer logistics;

    @JsonProperty("return")
    private Integer returnValue;

}
