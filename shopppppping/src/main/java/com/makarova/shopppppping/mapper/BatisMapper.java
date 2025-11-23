package com.makarova.shopppppping.mapper;

import com.makarova.shopppppping.domain.Product;
import com.makarova.shopppppping.domain.ProductForUpload;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface BatisMapper {

    void insetToUpload(@Param("productName") String productName);

    void updateProductsToUpload(@Param("productForUpload") ProductForUpload productForUpload);

    Optional<ProductForUpload> findProductForUpload();

    void insertToProducts(@Param("product") Product product);
}
