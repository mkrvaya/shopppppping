package com.makarova.shopppppping.scheduler;

import com.makarova.shopppppping.client.WbRestClient;
import com.makarova.shopppppping.domain.Product;
import com.makarova.shopppppping.domain.ProductForUpload;
import com.makarova.shopppppping.mapper.BatisMapper;
import com.makarova.shopppppping.model.CatalogResponseDto;
import com.makarova.shopppppping.model.ProductDto;
import com.makarova.shopppppping.model.SizeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class WbScheduledTask {

    private final WbRestClient wbRestClient;
    private final BatisMapper batisMapper;

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        Optional<ProductForUpload> productForUploadExists = batisMapper.findProductForUpload();
        if (productForUploadExists.isEmpty()) {
            log.info("All product uploaded");
            return;
        }
        ProductForUpload productForUpload = productForUploadExists.get();
        log.info("productForUpload {}", productForUpload);
        productForUpload.setLastPageNumber(productForUpload.getLastPageNumber() == null ? 0L : productForUpload.getLastPageNumber());
        CatalogResponseDto productsByFilter = wbRestClient.getProductsByFilter(productForUpload.getProductName(), productForUpload.getLastPageNumber() + 1);

        List<ProductDto> responseProducts = productsByFilter.getProducts();
        if (isNotEmpty(responseProducts)) {
            List<Product> wbProducts = responseProducts.stream().map(rp -> {
                Product product = new Product();
                product.setProductName(rp.getName());
                product.setProductBrand(rp.getBrand());
                product.setExternalProductId(rp.getId());
                product.setFeedbacks(rp.getFeedbacks());
                product.setPrice(rp.getSizes().stream().map(size -> size.getPrice().getProduct()).findFirst().orElse(null));
                product.setReviewRating(rp.getReviewRating() != null ? String.valueOf(rp.getReviewRating()) : null);
                product.setSource("WB");
                product.setTotalQuantity(rp.getTotalQuantity());
                product.setSizes(rp.getSizes().stream().map(SizeDto::getName).collect(Collectors.joining(";")));
                return product;
            }).collect(toList());

            wbProducts.forEach(batisMapper::insertToProducts);

            productForUpload.setLastSyncDate(LocalDateTime.now());
//            if (productForUpload.getLastPageNumber() * 100 >= productsByFilter.getTotal()) {
//                productForUpload.setIsLast(Boolean.TRUE);
//            }
            productForUpload.setLastPageNumber(productForUpload.getLastPageNumber() == null ? 1L : productForUpload.getLastPageNumber() + 1);

            batisMapper.updateProductsToUpload(productForUpload);
        } else {
            productForUpload.setLastSyncDate(LocalDateTime.now());
            productForUpload.setIsLast(Boolean.TRUE);
            batisMapper.updateProductsToUpload(productForUpload);
        }

    }
}
