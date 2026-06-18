package com.sjorge.product_service.service;

import com.sjorge.product_service.dto.ProductRequest;
import com.sjorge.product_service.dto.ProductResponse;
import com.sjorge.product_service.entity.Product;
import com.sjorge.product_service.exception.ProductNotFoundException;
import com.sjorge.product_service.mapper.ProductMapper;
import com.sjorge.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository,
                          ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ProductResponse createProduct(ProductRequest request) {

        Product product = mapper.toEntity(request);

        Product saved = repository.save(product);

        return mapper.toResponse(saved);
    }

    public List<ProductResponse> getProducts() {

        return mapper.toResponseList(
                repository.findAll()
        );
    }

    public ProductResponse getById(Long id) {

        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id));

        mapper.updateEntity(request, product);

        Product updated = repository.save(product);

        return mapper.toResponse(updated);
    }

    public void deleteProduct(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(id));

        repository.delete(product);
    }
}