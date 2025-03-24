package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.LigneCommandeDao;
import com.hrizzon.demo2.model.LigneCommande;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class LigneCommandeController {

    protected LigneCommandeDao ligneCommandeDao;

    @Autowired
    public LigneCommandeController(LigneCommandeDao ligneCommandeDao) {
        this.ligneCommandeDao = ligneCommandeDao;
    }

    @GetMapping("/lignecommande/{id}")
    public ResponseEntity<LigneCommande> get(@PathVariable int id) {

        Optional<LigneCommande> optionalLigneCommande = ligneCommandeDao.findById(id);

        if (optionalLigneCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalLigneCommande.get(), HttpStatus.OK);
    }

    @GetMapping("/lignecommandes")
    public List<LigneCommande> getAll() {

        return ligneCommandeDao.findAll();
    }

    @PostMapping("/lignecommande")
    public ResponseEntity<LigneCommande> save(@RequestBody @Valid LigneCommande ligneCommande) {

        ligneCommandeDao.save(ligneCommande);

        return new ResponseEntity<>(ligneCommande, HttpStatus.CREATED);
    }

    @DeleteMapping("/lignecommande/{id}")
    public ResponseEntity<LigneCommande> delete(@PathVariable int id) {

        Optional<LigneCommande> optionalLigneCommande = ligneCommandeDao.findById(id);

        if (optionalLigneCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ligneCommandeDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/lignecommande/{id}")
    public ResponseEntity<LigneCommande> update(
            @PathVariable int id,
            @RequestBody @Valid LigneCommande lignecommande) {

        Optional<LigneCommande> optionalLigneCommande = ligneCommandeDao.findById(id);

        if (optionalLigneCommande.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        lignecommande.setId(id);

        ligneCommandeDao.save(lignecommande);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
