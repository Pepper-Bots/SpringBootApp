package com.hrizzon.demo2.model;

import com.hrizzon.demo2.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    protected String email;

    @NotBlank
    @Column(nullable = false)
    protected String password;

    //    protected boolean admin; // -> dans l'optique de n'avoir à gérer que 2 rôles : ADMIN & USER
//    @Enumerated(EnumType.STRING)
//    @Column(columnDefinition = "ENUM('USER','REDACTEUR','ADMIN')")
//    protected Role role;
}
