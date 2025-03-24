package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.ProductDao;
import com.hrizzon.demo2.model.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProductController {

//    @Autowired
//    protected ProductDao productDao;

    protected ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> get(@PathVariable int id) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
    }

    @GetMapping("/products")
    public List<Product> getAll() {


        return productDao.findAll();
    }

    @PostMapping("/product")
    public ResponseEntity<Product> save(@RequestBody @Valid Product product) {

        productDao.save(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/product/{id}")
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
