package com.hrizzon.demo2;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.TimeZone;

// Annotation : pour signifier que le programme se base sur ce qui est écrit ici, pas ailleurs
@SpringBootApplication
public class Demo2Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    // On met la dépendance ici plutot que dans l'application sinon ça faite une dépendance récursive
    @Bean // @Bean facilement remplaçables quand on fait des tests unitaires
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // a chaque fois que j'aurais besoin d'un objet password encoder -> je recupère un BCryptPasswordEncoder
    }
}
