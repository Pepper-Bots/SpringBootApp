package com.hrizzon.demo2.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.view.AffichageCommande;
import com.hrizzon.demo2.view.AffichageProductPourClient;
import com.hrizzon.demo2.view.AffichageProductPourVendeur;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter // Annotation Lombok qui génère automatiquement les méthodes getter pour tous les champs de la classe.
@Setter // Annotation Lombok qui génère automatiquement les méthodes setter pour tous les champs de la classe.
@Entity
// Annotation JPA (Java Persistence API) qui marque cette classe comme une entité, c'est-à-dire une table dans la base de données.
// Le nom de la table sera par défaut "product" (en minuscules).
public class Product {

    @Id // Annotation JPA qui spécifie que le champ 'id' est la clé primaire de la table 'product'.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Annotation JPA qui configure la stratégie de génération de la clé primaire. IDENTITY signifie que la base de données gérera l'auto-incrémentation de l'ID.
    @JsonView({AffichageProductPourClient.class})
    protected Integer id; // Identifiant unique du produit. Sera la clé primaire dans la table correspondante.

    @Column(nullable = false)
    // Annotation JPA qui spécifie que la colonne 'nom' dans la base de données ne peut pas être nulle.
    @JsonView({AffichageCommande.class, AffichageProductPourClient.class})
    // Annotation Jackson qui indique que ce champ doit être inclus dans les vues JSON lors de la sérialisation où la classe 'AffichageCommande' est spécifiée. Cela permet de contrôler les informations renvoyées dans certaines API. On peut imaginer que 'AffichageCommande' est une interface ou une classe utilisée pour définir les champs à afficher lors de la récupération de commandes.
    protected String nom; // Nom du produit.

    @Column(nullable = false, unique = true)
    // Annotation JPA qui configure la colonne 'codeArticle' : sa longueur maximale est de 10 caractères, elle ne peut pas être nulle et sa valeur doit être unique dans toute la table.
    @Length(max = 10, min = 3, message = "Longueur entre 3 et 10")
    // Annotation Hibernate Validator qui impose une contrainte sur la longueur de la chaîne 'codeArticle' (entre 3 et 10 caractères). Cette validation sera effectuée au niveau de l'application avant la persistance en base de données.
//    @NotBlank
    // Annotation Hibernate Validator qui assure que le champ 'codeArticle' n'est ni nul ni une chaîne de caractères ne contenant que des espaces blancs.
//    @JsonView(AffichageCommande.class)
    // Indique que ce champ sera inclus dans les vues JSON utilisant 'AffichageCommande'.
    @JsonView({AffichageProductPourClient.class})
    protected String codeArticle; // Code unique identifiant l'article.

    @Column(columnDefinition = "TEXT")
    // Annotation JPA qui spécifie le type de la colonne 'description' dans la base de données comme étant un type TEXT, permettant de stocker de longues chaînes de caractères.
    @JsonView({AffichageProductPourClient.class})
    protected String description; // Description détaillée du produit.

    @DecimalMin(value = "0.1")
    // Annotation Hibernate Validator qui impose une valeur minimale de 0.1 pour le champ 'prix'.
    @JsonView({AffichageProductPourClient.class})
    protected float prix;

    @ManyToOne
    // Annotation JPA qui définit une relation "plusieurs-à-un" (Many-to-One) entre l'entité 'Product' et l'entité 'Etat'. Plusieurs produits peuvent avoir le même état, mais chaque produit est associé à un seul état.
    // table
    @JoinColumn(nullable = false)
    // Annotation JPA qui spécifie la colonne de clé étrangère dans la table 'product' qui référence la table 'etat'. 'nullable = false' signifie que chaque produit doit obligatoirement être associé à un état. Le nom de la colonne sera par défaut 'etat_id'.
    @JsonView({AffichageProductPourClient.class})
    protected Etat etat; // L'état actuel du produit (par exemple, "En stock", "Rupture de stock", "En promotion"). On peut imaginer qu'il existe une classe 'Etat' dans le même package ou un autre, annotée avec '@Entity' et représentant les différents états possibles.

    @ManyToMany
    // Annotation JPA qui définit une relation "plusieurs-à-plusieurs" (Many-to-Many) entre l'entité 'Product' et l'entité 'Etiquette'. Un produit peut avoir plusieurs étiquettes (par exemple, "Nouveau", "Soldé", "Bio"), et une étiquette peut être associée à plusieurs produits.
    // table de jointure
    @JoinTable(
            name = "etiquette_product", // Spécifie le nom de la table de jointure qui sera créée dans la base de données pour gérer cette relation Many-to-Many.
//            joinColumns = @JoinColumn(name = "product_id"), // Définit la colonne de clé étrangère dans la table de jointure qui référence la table 'product'. Par défaut, Spring Data JPA pourrait la nommer 'product_id'. Cette ligne est commentée, ce qui signifie que Spring Data JPA utilisera probablement une convention de nommage par défaut basée sur le nom de l'entité.
            inverseJoinColumns = @JoinColumn(name = "etiquette_id") // Définit la colonne de clé étrangère dans la table de jointure qui référence la table 'etiquette'. Par défaut, Spring Data JPA pourrait la nommer 'etiquette_id'.
    )
    protected List<Etiquette> etiquettes = new ArrayList<>(); // Liste des étiquettes associées à ce produit. On peut imaginer qu'il existe une classe 'Etiquette' annotée avec '@Entity' représentant les différentes étiquettes. L'initialisation avec 'new ArrayList<>()' assure qu'une liste vide est créée par défaut si aucune étiquette n'est ajoutée initialement.
    // ou Listetiquette ou etiquetteListe → jms etiquette au singulier

    @ManyToOne
    // Annotation JPA qui définit une relation "plusieurs-à-un" (Many-to-One) entre l'entité 'Product' et l'entité 'Vendeur'. Plusieurs produits peuvent être créés par le même vendeur, mais chaque produit est associé à un seul créateur.
    @JoinColumn(nullable = false)
    // Annotation JPA qui spécifie la colonne de clé étrangère dans la table 'product' qui référence la table 'vendeur'. 'nullable = false' signifie que chaque produit doit obligatoirement avoir un créateur. Le nom de la colonne sera par défaut 'createur_id'.
    @JsonView(AffichageProductPourVendeur.class)
    Vendeur createur; // Le vendeur qui a créé ce produit. On peut imaginer qu'il existe une classe 'Vendeur' annotée avec '@Entity' représentant les informations des vendeurs.


}
