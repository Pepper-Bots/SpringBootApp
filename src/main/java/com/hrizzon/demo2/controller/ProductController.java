package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.ProductDao;
import com.hrizzon.demo2.model.Etat;
import com.hrizzon.demo2.model.Product;
import com.hrizzon.demo2.model.Vendeur;
import com.hrizzon.demo2.security.AppUserDetails;
import com.hrizzon.demo2.security.IsClient;
import com.hrizzon.demo2.security.IsVendeur;
import com.hrizzon.demo2.security.SecuriteUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class ProductController {

//    @Autowired
//    protected ProductDao productDao;

    protected ProductDao productDao;
    protected SecuriteUtils securiteUtils;

    @Autowired
    public ProductController(ProductDao productDao, SecuriteUtils securiteUtils) {
        this.productDao = productDao;
        this.securiteUtils = securiteUtils;
    }

    @GetMapping("/product/{id}")
//    @IsAdmin
    @IsClient
    public ResponseEntity<Product> get(@PathVariable int id) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(optionalProduct.get(), HttpStatus.OK);
    }

    @GetMapping("/products")
    @IsClient
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @PostMapping("/product")
    @IsVendeur
    public ResponseEntity<Product> save(
            @RequestBody @Valid Product product,
            @AuthenticationPrincipal AppUserDetails userDetails) { // On récupère toutes les infos de la personne qui est connectée

        product.setCreateur((Vendeur) userDetails.getUtilisateur());

        // Dans le cas d'un enum
        // product.setCreateur(userDetails.getUtilisateur());
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
    @IsVendeur
    public ResponseEntity<Product> delete(
            @PathVariable int id,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {

        Optional<Product> optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

//        String role = userDetails.getAuthorities().stream()
//                .map(r -> r.getAuthority()) // permet de modifier tous les roles en chaine de txt
//                .findFirst()
//                .get();

        // Des que SecuriteUtils est crée on l'injecte ici pour récupérer le rôle de la personne
        String role = securiteUtils.getRole(userDetails);

        // Pour la méthode sans stream :
//        String role = ((SimpleGrantedAuthority)userDetails.getAuthorities().toArray()[0]).getAuthority();


        // récupérer le role de la personne et si il n'est ni chef de rayon ni proprio de la ressource j'envoie un 403
        // Si l'id du createur du produit est != de l'id de la personne connectée
        // Securisation de la ressource : je suis bien vendeur mais c'est pas ma ressource donc je n'ai pas le droit de le faire
        if (!role.equals("ROLE_CHEF_RAYON") ||
                (optionalProduct.get().getCreateur().getId() != userDetails.getUtilisateur().getId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        productDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/product/{id}")
    @IsVendeur
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
