package com.makarova.shopppppping.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResponseDto {

    private MetadataDto metadata;
    private List<ProductDto> products;
    private Integer total;
}
