package com.hrizzon.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.view.AffichageCommande;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @NotNull
    @JsonView(AffichageCommande.class)
    protected LocalDateTime date;

    // List = interface pour ArrayList
    // qd on initialise une liste toujours l'instancier à vide
    @OneToMany(mappedBy = "commande")
    // @OneToMany ne peut pas exister sans @ManytoOne de l'autre côté -> relation faible
    protected List<LigneCommande> lignes = new ArrayList<>();
//    protected Set<LigneCommande> lignes = new HashSet<>();  -> le Set enlève les doublons


}
