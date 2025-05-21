package com.hrizzon.demo2.controller;

import com.hrizzon.demo2.dao.ClientDao;
import com.hrizzon.demo2.dao.UtilisateurDao;
import com.hrizzon.demo2.dto.ValidationEmailDto;
import com.hrizzon.demo2.model.Client;
import com.hrizzon.demo2.model.Utilisateur;
import com.hrizzon.demo2.security.AppUserDetails;
import com.hrizzon.demo2.security.ISecuriteUtils;
import com.hrizzon.demo2.service.EmailService;
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

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@CrossOrigin
@RestController
public class AuthController {

    protected UtilisateurDao utilisateurDao;
    protected ClientDao clientDao;
    protected PasswordEncoder passwordEncoder;
    protected AuthenticationProvider authenticationProvider;
    protected ISecuriteUtils securiteUtils;
    protected EmailService emailService;

    @Autowired
    public AuthController(UtilisateurDao utilisateurDao, ClientDao clientDao, PasswordEncoder passwordEncoder,
                          AuthenticationProvider authenticationProvider, ISecuriteUtils securiteUtils, EmailService emailService) {
        this.utilisateurDao = utilisateurDao;
        this.clientDao = clientDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationProvider = authenticationProvider;
        this.securiteUtils = securiteUtils;
        this.emailService = emailService;
    }


    @PostMapping("/inscription")
    public ResponseEntity<Client> inscription(@RequestBody @Valid Utilisateur utilisateur) throws IOException {

        //utilisateur.setRole(Role.UTILISATEUR);

        Client client = new Client();
        client.setNumero(UUID.randomUUID().toString());
        client.setEmail(utilisateur.getEmail());
        client.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

        String tokenValidationEmail = UUID.randomUUID().toString();

        client.setJetonVerificationEmail(tokenValidationEmail);
        clientDao.save(client);
        emailService.sendEmailValidationToken(client.getEmail(), tokenValidationEmail);

        //on masque le mot de passe et le jeton de vérification
        client.setPassword(null);
        client.setJetonVerificationEmail(null);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody @Valid Utilisateur utilisateur) {

        try {
            AppUserDetails userDetails = (AppUserDetails) authenticationProvider
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    utilisateur.getEmail(),
                                    utilisateur.getPassword()))
                    .getPrincipal();

            return new ResponseEntity<>(securiteUtils.generateToken(userDetails), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/validate-email")
    public ResponseEntity<Utilisateur> validateEmail(@RequestBody ValidationEmailDto validationEmailDto) {

        Optional<Utilisateur> utilisateur = utilisateurDao.findByJetonVerificationEmail(validationEmailDto.getToken());

        if (utilisateur.isPresent()) {
            utilisateur.get().setJetonVerificationEmail(null);
            utilisateurDao.save(utilisateur.get());
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}