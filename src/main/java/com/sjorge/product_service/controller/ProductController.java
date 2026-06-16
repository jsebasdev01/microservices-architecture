package com.sjorge.product_service.controller;

import com.sjorge.product_service.api.ProductApi; // o DefaultApi
import com.sjorge.product_service.dto.ProductRequest;
import com.sjorge.product_service.dto.ProductResponse;
import com.sjorge.product_service.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(
                productService.getProducts()
        );
    }

    @Override
    public ResponseEntity<ProductResponse> createProduct(
            ProductRequest productRequest) {

        ProductResponse response =
                productService.createProduct(productRequest);

        return ResponseEntity.status(201)
                .body(response);
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(Long id) {

        return ResponseEntity.ok(
                productService.getById(id)
        );
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {

        return ResponseEntity.ok(
                productService.updateProduct(
                        id,
                        productRequest
                )
        );
    }

    @Override
    public ResponseEntity<Void> deleteProduct(Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}