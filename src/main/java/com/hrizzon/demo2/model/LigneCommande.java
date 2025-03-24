package com.hrizzon.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.view.AffichageCommande;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AffichageCommande.class)
    protected Integer id;

    @DecimalMin(value = "0.1")
    @JsonView(AffichageCommande.class)
    protected float prixVente; // pas besoin de mettre nullable parce que type primitif -> automatique

    @Min(1)
    @JsonView(AffichageCommande.class)
    protected int quantite; // pas besoin de mettre nullable parce que type primitif -> automatique

    @ManyToOne
    @JoinColumn(nullable = false)
    @JsonView(AffichageCommande.class)
    protected Product product;

    @ManyToOne // -> Défini la liaison avec le @OnetoMany
    @JoinColumn(nullable = false)
//    @JsonIgnore
    protected Commande commande;

}
