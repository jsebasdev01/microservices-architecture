package com.sjorge.product_service.service;

import com.sjorge.product_service.dto.ProductRequest;
import com.sjorge.product_service.dto.ProductResponse;
import com.sjorge.product_service.entity.Product;
import com.sjorge.product_service.exception.ProductNotFoundException;
import com.sjorge.product_service.mapper.ProductMapper;
import com.sjorge.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldCreateProduct() {

        ProductRequest request = new ProductRequest();

        request.setName("Laptop");
        request.setPrice(BigDecimal.valueOf(1000));
        request.setStock(5);

        Product entity = new Product();
        entity.setName("Laptop");

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Laptop");

        ProductResponse response = new ProductResponse();
        response.setId(1L);

        when(mapper.toEntity(request))
                .thenReturn(entity);

        when(repository.save(entity))
                .thenReturn(saved);

        when(mapper.toResponse(saved))
                .thenReturn(response);

        ProductResponse result =
                service.createProduct(request);

        assertThat(result.getId())
                .isEqualTo(1L);

        verify(repository).save(entity);
    }

    @Test
    void shouldReturnProductById() {

        Product entity = new Product();
        entity.setId(1L);

        ProductResponse response = new ProductResponse();
        response.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(entity));

        when(mapper.toResponse(entity))
                .thenReturn(response);

        ProductResponse result =
                service.getById(1L);

        assertThat(result.getId())
                .isEqualTo(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.getById(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void shouldUpdateProduct() {

        Product existing = new Product();
        existing.setId(1L);

        ProductRequest request = new ProductRequest();
        request.setName("Updated");

        ProductResponse response = new ProductResponse();
        response.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(repository.save(existing))
                .thenReturn(existing);

        when(mapper.toResponse(existing))
                .thenReturn(response);

        ProductResponse result =
                service.updateProduct(1L, request);

        assertThat(result.getId())
                .isEqualTo(1L);

        verify(repository).save(existing);
    }

    @Test
    void shouldDeleteProduct() {

        Product product = new Product();
        product.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(product));

        service.deleteProduct(1L);

        verify(repository)
                .delete(product);
    }

    @Test
    void shouldCreateProductWithZeroPrice() {

        ProductRequest request = new ProductRequest();
        request.setName("Free product");
        request.setPrice(BigDecimal.ZERO);

        Product entity = new Product();
        Product saved = new Product();
        ProductResponse response = new ProductResponse();

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ProductResponse result = service.createProduct(request);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldReturnAllProducts() {

        Product p1 = new Product();
        Product p2 = new Product();

        when(repository.findAll())
                .thenReturn(List.of(p1, p2));

        when(mapper.toResponse(p1))
                .thenReturn(new ProductResponse());

        when(mapper.toResponse(p2))
                .thenReturn(new ProductResponse());

        List<ProductResponse> result =
                service.getProducts();

        assertThat(result)
                .hasSize(2);
    }

    @Test
    void shouldReturnEmptyListWhenNoProductsExist() {

        when(repository.findAll())
                .thenReturn(Collections.emptyList());

        List<ProductResponse> result =
                service.getProducts();

        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {

        ProductRequest request = new ProductRequest();

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.updateProduct(1L, request))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {

        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.deleteProduct(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void shouldMapRequestToEntity() {

        ProductRequest request = new ProductRequest();

        Product entity = new Product();

        when(mapper.toEntity(request))
                .thenReturn(entity);

        when(repository.save(entity))
                .thenReturn(entity);

        when(mapper.toResponse(entity))
                .thenReturn(new ProductResponse());

        service.createProduct(request);

        verify(mapper)
                .toEntity(request);
    }
}
