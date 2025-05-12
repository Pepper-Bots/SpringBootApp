package com.hrizzon.demo2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    protected String message;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected Utilisateur auteur;

    @ManyToOne
    @JoinColumn(nullable = false)
    protected Utilisateur destinataire;

}
