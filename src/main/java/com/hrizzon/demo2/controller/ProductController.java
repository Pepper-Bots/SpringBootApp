package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.ProductDao;
import com.hrizzon.demo2.model.Etat;
import com.hrizzon.demo2.model.Product;
import com.hrizzon.demo2.security.ISecuriteUtils;
import com.hrizzon.demo2.security.IsClient;
import com.hrizzon.demo2.security.IsVendeur;
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
public class ProductController {

//    @Autowired
//    protected ProductDao productDao;

    protected ProductDao productDao;
    protected ISecuriteUtils securiteUtils;

    @Autowired
    public ProductController(ProductDao productDao, ISecuriteUtils securiteUtils) {
        this.productDao = productDao;
        this.securiteUtils = securiteUtils;
    }

    @GetMapping("/product/{id}")
//    @IsAdmin
    public ResponseEntity<Product> get(@PathVariable int id) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
    }

    @GetMapping("/products")
    @IsVendeur
    public List<Product> getAll() {


        return productDao.findAll();
    }

    @PostMapping("/product")
    public ResponseEntity<Product> save(@RequestBody @Valid Product product) {

        // Si le produit reçu n'a pas d'état alors on indique qu'il est neuf par défaut
        if (product.getEtat() == null) {

            Etat etatNeuf = new Etat();
            etatNeuf.setId(1);
            product.setEtat(etatNeuf);
        }

        product.setId(null);
        productDao.save(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{id}")
    @IsClient
    public ResponseEntity<Product> delete(@PathVariable int id) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        productDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<Product> update(
            @PathVariable int id,
            @RequestBody @Valid Product product) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        product.setId(id);

        productDao.save(product);

        return new ResponseEntity<>(product, HttpStatus.NO_CONTENT);
    }
}
