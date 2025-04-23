package com.ecommerce.order.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@WebMvcTest(ProductClient.class)
class ProductClientTest {

    @MockBean
    private ProductClient productClient;

    @Test
    void shouldGetProductById() {
        // Arrange
        Long productId = 1L;
        ProductResponse expectedProduct = new ProductResponse(
            productId,
            "Test Product",
            "Test Description",
            99.99,
            10
        );

        when(productClient.getProduct(productId)).thenReturn(expectedProduct);

        // Act
        ProductResponse actualProduct = productClient.getProduct(productId);

        // Assert
        assertNotNull(actualProduct);
        assertEquals(expectedProduct.id(), actualProduct.id());
        assertEquals(expectedProduct.name(), actualProduct.name());
        assertEquals(expectedProduct.description(), actualProduct.description());
        assertEquals(expectedProduct.price(), actualProduct.price());
        assertEquals(expectedProduct.stock(), actualProduct.stock());
    }
}
