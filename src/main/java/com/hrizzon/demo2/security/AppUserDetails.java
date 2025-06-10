package com.hrizzon.demo2.security;


import com.hrizzon.demo2.model.Utilisateur;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class AppUserDetails implements UserDetails {

    protected Utilisateur utilisateur;

    public AppUserDetails(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_" + utilisateur.getNomRole()));

//        boolean isClient = utilisateur instanceof Client;
//
//        if (isClient) {
//            return List.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
//        } else {
//
//            Vendeur vendeur = (Vendeur) utilisateur;
//            return List.of(new SimpleGrantedAuthority("ROLE_" + (vendeur.isChef() ? "CHEF_RAYON" : "VENDEUR")));
//        }

//        return List.of(new SimpleGrantedAuthority(
////                "ROLE_" + utilisateur.getRole().name()));
//                isClient ? "ROLE_CLIENT" : "ROLE_VENDEUR"));
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.getJetonVerificationEmail() == null;
    }
}
