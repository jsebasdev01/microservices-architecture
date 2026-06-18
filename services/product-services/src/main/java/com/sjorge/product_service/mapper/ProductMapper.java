package com.sjorge.product_service.mapper;

import com.sjorge.product_service.dto.ProductRequest;
import com.sjorge.product_service.dto.ProductResponse;
import com.sjorge.product_service.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    void updateEntity(
            ProductRequest request,
            @MappingTarget Product product
    );
}
