package com.hrizzon.demo2.security;

import com.hrizzon.demo2.dao.ClientDao;
import com.hrizzon.demo2.dao.VendeurDao;
import com.hrizzon.demo2.model.Client;
import com.hrizzon.demo2.model.Vendeur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
// exactement pareil que @repository qui met aussi en place une queryMethod -> le but de @Service est simplement de créer une dépendance
public class AppUserDetailsService implements UserDetailsService { // Il doit implémenter le User Detail Service puisque c'est de ça que j'ai besoin =>

    protected ClientDao clientDao;
    protected VendeurDao vendeurDao;

    @Autowired
    public AppUserDetailsService(ClientDao clientDao, VendeurDao vendeurDao) {
        this.clientDao = clientDao;
        this.vendeurDao = vendeurDao;
    }

    // => On va donc implémenter la méthode UserDetails
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Optional<Client> optionalClient = clientDao.findByEmail(email); // je récupère un utilisateur avec finfByEmail qui permet de récup un user par son email

        if (optionalClient.isEmpty()) {

            Optional<Vendeur> optionalVendeur = vendeurDao.findByEmail(email);

            if (optionalVendeur.isEmpty()) {
                throw new UsernameNotFoundException(email);
            } else {
                return new AppUserDetails(optionalVendeur.get());
            }
        } else {
            return new AppUserDetails(optionalClient.get());
        }
    }
}

