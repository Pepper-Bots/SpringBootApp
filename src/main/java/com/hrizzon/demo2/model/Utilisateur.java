package com.hrizzon.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.view.AffichageUtilisateur;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "nom_role", discriminatorType = DiscriminatorType.STRING)
public class Utilisateur {

    public interface ValidInscription {
    }

    public interface ValidModification {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AffichageUtilisateur.class)
    protected Integer id;

    @NotBlank(groups = {ValidInscription.class})
    @Email
    @Column(unique = true, nullable = false)
    protected String email;

    @NotBlank(groups = {ValidInscription.class})
    @Column(nullable = false)
    protected String password;

    @OneToMany(mappedBy = "destinataire", fetch = FetchType.LAZY)
    protected List<Notification> notifications;

    protected String jetonVerificationEmail;

    @Column(name = "nom_role", insertable = false, updatable = false)
    protected String nomRole;
    // TODO 0 à terminer


}
