package com.makarova.shopppppping.resource;

import com.makarova.shopppppping.client.WbRestClient;
import com.makarova.shopppppping.domain.Product;
import com.makarova.shopppppping.mapper.BatisMapper;
import com.makarova.shopppppping.model.CatalogResponseDto;
import com.makarova.shopppppping.model.ProductDto;
import com.makarova.shopppppping.model.SizeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MyFirstApi {

    private final WbRestClient wbRestClient;
    private final BatisMapper batisMapper;

//    @GetMapping("/v1/first-api")
//    public CatalogResponseDto getHello(@RequestParam(name = "productName") String productName) {
//        log.info("Hello Mak Mak! Go to The {}", productName);
//        batisMapper.insetToUpload(productName);
//        var res = batisMapper.findProductForUpload();
//        log.info(res.toString());
//        CatalogResponseDto productsByFilter = wbRestClient.getProductsByFilter(productName, 1L);
//
//        List<ProductDto> responseProducts = productsByFilter.getProducts();
//        if (isNotEmpty(responseProducts)) {
//            List<Product> wbProducts = responseProducts.stream().map(rp -> {
//                Product product = new Product();
//                product.setProductName(rp.getName());
//                product.setProductBrand(rp.getBrand());
//                product.setExternalProductId(rp.getId());
//                product.setFeedbacks(rp.getFeedbacks());
//                product.setPrice(rp.getSizes().stream().map(size -> size.getPrice().getProduct()).findFirst().orElse(null));
//                product.setReviewRating(rp.getReviewRating() != null ? String.valueOf(rp.getReviewRating()) : null);
//                product.setSource("WB");
//                product.setTotalQuantity(rp.getTotalQuantity());
//                product.setSizes(rp.getSizes().stream().map(SizeDto::getName).collect(Collectors.joining(";")));
//                return product;
//            }).collect(toList());
//            wbProducts.forEach(batisMapper::insertToProducts);
//
//            log.info("Got products {} and total count {}", wbProducts.size(), productsByFilter.getTotal());
//        }
//
//        return productsByFilter;
//    }

    @PutMapping("/v1/first-api")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void insetToUpload(@RequestParam(name = "productName") List<String> productNames) {
        log.info("Hello Mak Mak! Go to The {}", productNames);
        productNames.forEach(batisMapper::insetToUpload);
    }
}


//f0d5e9e8f0d5e9e8f0d5e9e8d2f3e9e63cff0d5f0d5e9e8982e31a44542ceb0aabda583


//productName=organic people&productName=YOS DESIGN&productName=A1FA&productName=MATERIA&productName=Annemore&productName=Puro Lino Eco&productName=Chintamani&productName=Johnature Store&productName=EcoSense