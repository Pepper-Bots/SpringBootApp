package com.hrizzon.demo2.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.hrizzon.demo2.dao.VendeurDao;
import com.hrizzon.demo2.model.Notification;
import com.hrizzon.demo2.model.Vendeur;
import com.hrizzon.demo2.security.IsVendeur;
import com.hrizzon.demo2.view.AffichageUtilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@IsVendeur
public class VendeurController {

//    @Autowired
//    protected VendeurDao vendeurDao;

    protected VendeurDao vendeurDao;

    @Autowired
    public VendeurController(VendeurDao vendeurDao) {
        this.vendeurDao = vendeurDao;
    }

    @GetMapping("/vendeur/{id}")
//    @IsAdmin
    public ResponseEntity<Vendeur> get(@PathVariable int id) {

        Optional<Vendeur
                > optionalVendeur
                = vendeurDao.findById(id);

        if (optionalVendeur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalVendeur.get(), HttpStatus.OK);
    }

    @GetMapping("/vendeurs")
    public List<Vendeur> getAll() {

        List<Vendeur> vendeurs = vendeurDao.findAll();

        // Resolution du json infini manuellement (JsonView ne peut pas régler le pb)
        for (Vendeur vendeur : vendeurs) {
            for (Notification notification : vendeur.getNotifications()) {
                notification.getAuteur().setNotifications(null);
                notification.setDestinataire(null); // celui-ci peut-être géré avec un JsonView
            }
        }
        return vendeurs;
    }

    @PostMapping("/vendeur")
    @JsonView(AffichageUtilisateur.class)
    public ResponseEntity<Vendeur> save(@RequestBody @Valid Vendeur vendeur) {

        vendeur.setId(null);
        vendeurDao.save(vendeur);
        return new ResponseEntity<>(vendeur, HttpStatus.CREATED);
    }

    @DeleteMapping("/vendeur/{id}")
    @IsVendeur
    public ResponseEntity<Vendeur> delete(@PathVariable int id) {

        Optional<Vendeur> optionalVendeur = vendeurDao.findById(id);

        if (optionalVendeur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vendeurDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/vendeur/{id}")
    public ResponseEntity<Vendeur> update(
            @PathVariable int id,
            @RequestBody @Valid Vendeur vendeur) {

        Optional<Vendeur> optionalVendeur = vendeurDao.findById(id);

        if (optionalVendeur.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        vendeur.setId(id);

        vendeurDao.save(vendeur
        );

        return new ResponseEntity<>(vendeur, HttpStatus.NO_CONTENT);
    }
}
