package com.hrizzon.demo2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(ClePromotion.class)
public class Promotion {

    @Id
    protected Integer productId;

    @Id
    protected Integer typePromotionId;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("typePromotionId")
    @JoinColumn(name = "type_promotion_id")
    private TypePromotion typePromotion;

    protected Integer valeur;
}
