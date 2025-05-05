package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.PromotionDao;
import com.hrizzon.demo2.model.ClePromotion;
import com.hrizzon.demo2.model.Promotion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class PromotionController {

    protected PromotionDao promotionDao;

    @Autowired
    public PromotionController(PromotionDao promotionDao) {
        this.promotionDao = promotionDao;
    }

    @GetMapping("/promotion/{id}")
    public ResponseEntity<Promotion> get(
            @PathVariable int idPromotion,
            @PathVariable int idTypePromotion
    ) {

        Optional<Promotion> optionalPromotion = promotionDao.findById(new ClePromotion(idPromotion, idTypePromotion));

        if (optionalPromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalPromotion.get(), HttpStatus.OK);
    }

    @GetMapping("/promotions")
    public List<Promotion> getAll() {


        return promotionDao.findAll();
    }

    @PostMapping("/promotion")
    public ResponseEntity<Promotion> save(@RequestBody Promotion promotion) {
        promotionDao.save(promotion);

        return new ResponseEntity<>(promotion, HttpStatus.CREATED);
    }

    @DeleteMapping("/promotion/{id}")
    public ResponseEntity<Promotion> delete(@PathVariable int id) {

        Optional<Promotion> optionalPromotion = promotionDao.findById(new ClePromotion());

        if (optionalPromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        promotionDao.deleteById(new ClePromotion());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
//    @PutMapping("/promotion/{id}")
//    public ResponseEntity<Promotion> update(
//            @PathVariable int id,
//            @RequestBody Promotion promotion) {
//
//        Optional<Promotion> optionalPromotion = promotionDao.findById(id);
//
//        if (optionalPromotion.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        promotion.setId(id);
//
//        promotionDao.save(promotion);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}
