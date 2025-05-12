package com.hrizzon.demo2;

import com.hrizzon.demo2.controller.ProductController;
import com.hrizzon.demo2.mock.MockProductDao;
import com.hrizzon.demo2.mock.MockSecuriteUtils;
import com.hrizzon.demo2.model.Product;
import com.hrizzon.demo2.model.Vendeur;
import com.hrizzon.demo2.security.AppUserDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProductControllerTest {

    ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(
                new MockProductDao(), new MockSecuriteUtils("ROLE_VENDEUR"), null
        );
    }

    @Test
    void callGetWithExistingProduct_shouldSend200ok() {
        ProductController productController = new ProductController(
                new MockProductDao(), new MockSecuriteUtils("ROLE_VENDEUR"), null
        );

        ResponseEntity<Product> response = productController.get(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void callGetWithExistingProduct_shouldSend404notFound() {
        ProductController productController = new ProductController(
                new MockProductDao(), new MockSecuriteUtils("ROLE_VENDEUR"), null
        );

        ResponseEntity<Product> response = productController.get(1);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void deleteExistingProductBySellerOwner_shouldSend204noContent() {
        ProductController productController = new ProductController(
                new MockProductDao(), new MockSecuriteUtils("ROLE_CHEF_RAYON"), null
        );

        Vendeur fauxVendeur = new Vendeur();
        fauxVendeur.setId(1);
        AppUserDetails userDetails = new AppUserDetails(new Vendeur());

        ResponseEntity<Product> response = productController.delete(1, userDetails);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
