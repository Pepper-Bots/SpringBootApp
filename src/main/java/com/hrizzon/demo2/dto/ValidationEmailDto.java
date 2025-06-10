package com.hrizzon.demo2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationEmailDto {

    protected String token;
    protected boolean consentementUtilisationDonnees;
//protected String password; //< -- si l'utilisateur doit choisir son mot de passe a ce moment là

}