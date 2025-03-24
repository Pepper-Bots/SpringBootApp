package com.hrizzon.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.view.AffichageCommande;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    @JsonView(AffichageCommande.class)
    protected String nom;

    @Column(length = 10, nullable = false, unique = true)
    @Length(max = 10, min = 3)
    @NotBlank
    @JsonView(AffichageCommande.class)
    protected String codeArticle;

    @Column(columnDefinition = "TEXT")
    protected String description;

    @DecimalMin(value = "0.1")
    protected float prix;

    @ManyToOne
    // table
    @JoinColumn(nullable = false)
    protected Etat etat;

    @ManyToMany
    // table de jointure
    @JoinTable(
            name = "etiquette_product",
//            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id")
    )
    protected List<Etiquette> etiquettes = new ArrayList<>();
    // ou Listetiquette ou etiquetteListe → jms etiquette au singulier


}
