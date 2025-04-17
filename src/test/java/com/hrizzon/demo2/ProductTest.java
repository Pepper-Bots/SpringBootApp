package com.hrizzon.demo2;


import com.hrizzon.demo2.model.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void createValidProduct_shouldNotThrowsException() {

        Product productTest = new Product();
        productTest.setPrix(10);
        productTest.setNom("Test");
        productTest.setCodeArticle("Test");

        Set<ConstraintViolation<Product>> violations = validator.validate(productTest);

        assertTrue(violations.isEmpty());
    }

    @Test
    void createProductWithoutName_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setPrix(10);
        Set<ConstraintViolation<Object>> violations = validator.validate(productTest);

        boolean notBlankViolationExist = constraintExist(
                violations, "nom", "NotBlank");

        assertTrue(notBlankViolationExist);
    }

    @Test
    void createProductWithNegativePrice_shouldNotBeValid() {
        Product productTest = new Product();
        productTest.setNom("test");
        productTest.setPrix(-10);
        Set<ConstraintViolation<Object>> violations = validator.validate(productTest);

//        boolean decimalMinViolationExist = constraintExist(
//                violations, "prix", "DecimalMin");
//
//        Assertions.assertTrue(decimalMinViolationExist);
        // Suite à l'import de import static org.junit.jupiter.api.Assertions.*; -> Assertions peut être enlevé

        assertTrue(
                constraintExist(
                        validator.validate(productTest),
                        "prix",
                        "DecimalMin"));
    }

    private boolean constraintExist(Set<ConstraintViolation<Object>> violations, String fieldName, String constraintName) {
        return violations.stream()
                .filter(v -> v.getPropertyPath().toString().equals(fieldName))
                .map(v -> v.getConstraintDescriptor().getAnnotation().annotationType().getName())
                .anyMatch(s -> s.equals("jakarta.validation.constraints." + constraintName));
    }
}