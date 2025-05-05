package com.hrizzon.demo2.dao;

import com.hrizzon.demo2.model.ClePromotion;
import com.hrizzon.demo2.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionDao extends JpaRepository<Promotion, ClePromotion> {
}
