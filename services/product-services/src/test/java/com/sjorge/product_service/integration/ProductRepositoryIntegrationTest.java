package com.sjorge.product_service.integration;

import com.sjorge.product_service.entity.Product;
import com.sjorge.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class ProductRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("productdb")
                    .withUsername("postgres")
                    .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(
            DynamicPropertyRegistry registry) {

        registry.add(
                "spring.datasource.url",
                postgres::getJdbcUrl);

        registry.add(
                "spring.datasource.username",
                postgres::getUsername);

        registry.add(
                "spring.datasource.password",
                postgres::getPassword);
    }

    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldSaveProduct() {

        Product product = new Product();

        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(1200));
        product.setStock(10);

        Product saved =
                repository.save(product);

        assertThat(saved.getId())
                .isNotNull();
    }

    @Test
    void shouldFindProductById() {

        Product product = new Product();

        product.setName("Phone");
        product.setPrice(BigDecimal.valueOf(500));

        Product saved =
                repository.save(product);

        Optional<Product> found =
                repository.findById(saved.getId());

        assertThat(found)
                .isPresent();

        assertThat(found.get().getName())
                .isEqualTo("Phone");
    }
}
