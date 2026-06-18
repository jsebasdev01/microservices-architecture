package com.sjorge.product_service.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductExceptionHandlerTest {
    private final ProductExceptionHandler handler =
            new ProductExceptionHandler();
    @Test
    void shouldReturn404WhenProductNotFound() {

        ProductNotFoundException ex =
                new ProductNotFoundException(1L);

        ResponseEntity<ErrorResponse> response =
                handler.handler(ex);

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        assertThat(response.getBody())
                .isNotNull();

        assertThat(response.getBody().message())
                .contains("1");
    }
}
