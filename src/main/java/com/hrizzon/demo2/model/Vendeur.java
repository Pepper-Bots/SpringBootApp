package com.hrizzon.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.view.AffichageProductPourVendeur;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Vendeur extends Utilisateur {

    @JsonView({AffichageProductPourVendeur.class})
    int salaire;

    @JsonView({AffichageProductPourVendeur.class})
    boolean chef = false;

}
