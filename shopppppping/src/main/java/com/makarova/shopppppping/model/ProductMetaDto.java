package com.makarova.shopppppping.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductMetaDto {
    private List<String> tokens;

    @JsonProperty("presetId")
    private Long presetId;
}
