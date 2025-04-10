package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.UtilisateurDao;
import com.hrizzon.demo2.model.Utilisateur;
import com.hrizzon.demo2.security.AppUserDetails;
import com.hrizzon.demo2.security.JwUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class AuthController {

    protected UtilisateurDao utilisateurDao;
    protected PasswordEncoder passwordEncoder; // Va être changé à un seul endroit -> dans le DemoApplication
    protected AuthenticationProvider authenticationProvider;
    protected JwUtils jwUtils;

    @Autowired
    public AuthController(UtilisateurDao utilisateurDao, PasswordEncoder passwordEncoder, AuthenticationProvider authenticationProvider, JwUtils jwUtils) {
        this.utilisateurDao = utilisateurDao;
        this.passwordEncoder = passwordEncoder; // Autowire d'un passwordEncoder
        this.authenticationProvider = authenticationProvider;
        this.jwUtils = jwUtils;
    }

    // Méthode d'inscription d'un utilisateur
    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody @Valid Utilisateur utilisateur) {

//        utilisateur.setRole(Role.USER);
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateurDao.save(utilisateur);

        // On masque le mdp
        utilisateur.setPassword(null);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody @Valid Utilisateur utilisateur) {

        try {
            AppUserDetails userDetails = (AppUserDetails) authenticationProvider.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    utilisateur.getEmail(),
                                    utilisateur.getPassword()))
                    .getPrincipal();

            return new ResponseEntity<>(jwUtils.generateToken(userDetails), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
