package com.hrizzon.demo2.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
// @Target = savoir sur quoi je peux mettre cette annotation -> au dessus d'une méthode ou d'une classe
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole('ROLE_VENDEUR', 'ROLE_CHEF_RAYON')")
//> peut avoir n'importe quel rôle utilisateur ou admin pour avoir accès à la requête
//@PreAuthorized -> filtre -> va générer du code en fonction des annotations (comme une étiquette)
public @interface IsVendeur {
}
