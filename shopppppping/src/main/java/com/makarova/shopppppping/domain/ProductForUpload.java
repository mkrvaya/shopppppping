package com.makarova.shopppppping.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductForUpload {

    private Long id;

    private String productName;

    private LocalDateTime createDate;

    private LocalDateTime lastSyncDate;

    private Long lastPageNumber;

    private Boolean isLast;
}
