package com.hrizzon.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.view.AffichageUtilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.management.Notification;
import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AffichageUtilisateur.class)
    protected Integer id;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    protected String email;

    @NotBlank
    @Column(nullable = false)
    @JsonView()
    protected String password;

    @OneToMany(mappedBy = "destinataire")
    protected List<Notification> notifications;

    protected String jetonVerificationEmail;

    @Column(name = "nom_role", insertable = false, updatable = false)
    private String nomRole;

    //    protected boolean admin; // -> dans l'optique de n'avoir à gérer que 2 rôles : ADMIN & USER
//    @Enumerated(EnumType.STRING)
//    @Column(columnDefinition = "ENUM('USER','REDACTEUR','ADMIN')")
//    protected Role role;
}
