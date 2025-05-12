package com.hrizzon.demo2.dao;

import com.hrizzon.demo2.model.TypePromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePromotionDao extends JpaRepository<TypePromotion, Integer> {
}
