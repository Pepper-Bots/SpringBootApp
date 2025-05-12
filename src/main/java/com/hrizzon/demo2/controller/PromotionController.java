package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.ProductDao;
import com.hrizzon.demo2.dao.PromotionDao;
import com.hrizzon.demo2.dao.TypePromotionDao;
import com.hrizzon.demo2.model.ClePromotion;
import com.hrizzon.demo2.model.Product;
import com.hrizzon.demo2.model.Promotion;
import com.hrizzon.demo2.model.TypePromotion;
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
    protected ProductDao productDao;
    protected TypePromotionDao typePromotionDao;

    @Autowired
    public PromotionController(PromotionDao promotionDao, ProductDao productDao, TypePromotionDao typePromotionDao) {
        this.promotionDao = promotionDao;
        this.productDao = productDao;
        this.typePromotionDao = typePromotionDao;
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

        Optional<Product> optionalProduct = productDao.findById(promotion.getProductId());

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<TypePromotion> optionalTypePromotion = typePromotionDao.findById(promotion.getTypePromotionId());

        if (optionalTypePromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        promotionDao.save(promotion);

        return new ResponseEntity<>(promotion, HttpStatus.CREATED);
    }

    @DeleteMapping("/promotion/{idProduct}/{idTypePromotion}")
    public ResponseEntity<Promotion> delete(@PathVariable int id) {

        Optional<Promotion> optionalPromotion = promotionDao.findById(new ClePromotion());

        if (optionalPromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        promotionDao.deleteById(new ClePromotion());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //
    @PutMapping("/promotion/{idProduct}/{idTypePromotion}")
    public ResponseEntity<Promotion> update(
            @PathVariable int idProduct,
            @PathVariable int idTypePromotion,
            @RequestBody Promotion promotion) {

        Optional<Promotion> optionalPromotion = promotionDao.findById(
                new ClePromotion(idProduct, idTypePromotion));

        if (optionalPromotion.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Dans le contexte d'une promotion on autorise uniquement le changement de la valeur
        // Si l'utilisateur veut affecter la promotion a un autre produit / type, il supprime l'ancienne et crée une nouvelle

        optionalPromotion.get().setValeur(promotion.getValeur());

        promotionDao.save(optionalPromotion.get());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
