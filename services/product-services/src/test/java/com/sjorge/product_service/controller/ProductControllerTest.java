package com.sjorge.product_service.controller;

import com.sjorge.product_service.dto.ProductRequest;
import com.sjorge.product_service.dto.ProductResponse;
import com.sjorge.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    @Test
    void shouldCreateProduct() {

        ProductRequest request = new ProductRequest();

        ProductResponse response = new ProductResponse();
        response.setId(1L);

        when(service.createProduct(request))
                .thenReturn(response);

        ResponseEntity<ProductResponse> result =
                controller.createProduct(request);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        assertThat(result.getBody())
                .isEqualTo(response);
    }

    @Test
    void shouldGetProductById() {

        ProductResponse response =
                new ProductResponse();

        response.setId(1L);

        when(service.getById(1L))
                .thenReturn(response);

        ResponseEntity<ProductResponse> result =
                controller.getProductById(1L);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody().getId())
                .isEqualTo(1L);
    }

    @Test
    void shouldReturnAllProducts() {

        List<ProductResponse> products =
                List.of(new ProductResponse());

        when(service.getProducts())
                .thenReturn(products);

        ResponseEntity<List<ProductResponse>> response =
                controller.getProducts();

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(response.getBody())
                .hasSize(1);
    }

    @Test
    void shouldUpdateProduct() {

        ProductRequest request = new ProductRequest();

        ProductResponse response =
                new ProductResponse();

        response.setId(1L);

        when(service.updateProduct(1L, request))
                .thenReturn(response);

        ResponseEntity<ProductResponse> result =
                controller.updateProduct(1L, request);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        assertThat(result.getBody().getId())
                .isEqualTo(1L);
    }

    @Test
    void shouldDeleteProduct() {

        ResponseEntity<Void> response =
                controller.deleteProduct(1L);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);

        verify(service)
                .deleteProduct(1L);
    }

    @Test
    void shouldCallServiceWhenGettingById() {

        ProductResponse response =
                new ProductResponse();

        when(service.getById(1L))
                .thenReturn(response);

        controller.getProductById(1L);

        verify(service)
                .getById(1L);
    }
}
