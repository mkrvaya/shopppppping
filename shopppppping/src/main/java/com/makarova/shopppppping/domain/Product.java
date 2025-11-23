package com.makarova.shopppppping.domain;

import lombok.Data;

@Data
public class Product {

    private Long id;

    private String source;

    private Long externalProductId;

    private String productName;

    private String productBrand;

    private Long price;

    private String reviewRating;

    private Long feedbacks;

    private Long totalQuantity;

    private String sizes;
}
