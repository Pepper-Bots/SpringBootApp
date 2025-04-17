package com.hrizzon.demo2;

import com.hrizzon.demo2.controller.ProductController;
import com.hrizzon.demo2.mock.MockProducttDao;
import com.hrizzon.demo2.mock.MockSecuriteUtils;
import com.hrizzon.demo2.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProductControllerTest {

    @Test
    void callGetWithExistingProduct_shouldSend200ok() {

        ProductController productController = new ProductController(
                new MockProducttDao(), new MockSecuriteUtils()
        );

        ResponseEntity<Product> response = productController.get(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void callGetWithExistingProduct_shouldSend404notFound() {

        ProductController productController = new ProductController(
                new MockProducttDao(), new MockSecuriteUtils()
        );

        ResponseEntity<Product> response = productController.get(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
