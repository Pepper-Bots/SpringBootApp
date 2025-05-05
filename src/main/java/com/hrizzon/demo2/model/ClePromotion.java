package com.hrizzon.demo2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ClePromotion implements Serializable {


    @Column(name = "product_id")
    Integer productId;

    @Column(name = "type_promotion_id")
    Integer typePromotionId;
}
