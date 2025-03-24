package com.hrizzon.demo2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping({"/", "/hello"})
    public String sayHello() {
        return "<h1>Le serveur marche mais il n'y a rien à voir ici ! </h1>";
        // ouvrir localhost:8080 dans le nav -> msg s'affiche avec ou sans /hello
    }
}
