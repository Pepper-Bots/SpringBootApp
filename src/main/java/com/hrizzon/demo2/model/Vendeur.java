package com.hrizzon.demo2.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Vendeur extends Utilisateur {

    int salaire;

    boolean chef = false;

}
