package com.makarova.shopppppping.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private Integer time1;
    private Integer time2;
    private Integer wh;
    private Long dtype;
    private Integer dist;
    private Long root;

    @JsonProperty("kindId")
    private Integer kindId;

    private String brand;

    @JsonProperty("brandId")
    private Long brandId;

    @JsonProperty("siteBrandId")
    private Long siteBrandId;

    private List<ColorDto> colors;

    @JsonProperty("subjectId")
    private Integer subjectId;

    @JsonProperty("subjectParentId")
    private Integer subjectParentId;

    private String name;
    private String entity;

    @JsonProperty("matchId")
    private Long matchId;

    private String supplier;

    @JsonProperty("supplierId")
    private Long supplierId;

    @JsonProperty("supplierRating")
    private Double supplierRating;

    @JsonProperty("supplierFlags")
    private Long supplierFlags;

    private Integer pics;
    private Integer rating;

    @JsonProperty("reviewRating")
    private Double reviewRating;

    @JsonProperty("nmReviewRating")
    private Double nmReviewRating;

    private Long feedbacks;

    @JsonProperty("nmFeedbacks")
    private Integer nmFeedbacks;

    @JsonProperty("panelPromoId")
    private Long panelPromoId;

    private Integer volume;

    @JsonProperty("viewFlags")
    private Long viewFlags;

    private List<SizeDto> sizes;

    @JsonProperty("totalQuantity")
    private Long totalQuantity;

    private ProductMetaDto meta;

}
