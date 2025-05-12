package com.hrizzon.demo2.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = {FileValidator.class, ArrayFileValidator.class})
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
    String message() default "Type de fichier invalide ou taille de fichier trop grande.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] acceptedTypes() default {};

    long maxSize() default -1; // taille maximum en octet (-1 signifie pas de limite de taille)
}