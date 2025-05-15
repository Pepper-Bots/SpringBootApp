package com.hrizzon.demo2;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe IntercepteurExceptionGlobal
 * <p>
 * Cette classe est un gestionnaire global d'exceptions dans l'application Spring.
 * Elle permet de capturer certaines exceptions spécifiques levées lors du traitement des requêtes HTTP
 * et de retourner des réponses structurées au client avec les bons codes d'état HTTP.
 * <p>
 * Cette classe utilise l'annotation {@link ControllerAdvice} pour intercepter les erreurs à l'échelle globale du contrôleur.
 */
@ControllerAdvice
public class IntercepteurExceptionGlobal {

    // But ici est d'intercepter des erreurs

    /**
     * Intercepte les exceptions de type {@link MethodArgumentNotValidException},
     * qui surviennent lorsqu'une requête contient des arguments invalides,
     * notamment en cas d'erreur de validation sur les champs d'entrée (ex: @NotNull, @Size...).
     *
     * @param ex l'exception levée lors de la validation des arguments
     * @return une map contenant les messages d'erreur associés aux champs invalides, avec un code HTTP 400 (BAD_REQUEST)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> intercepteurViolationContrainte(MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = error.getDefaultMessage();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }

    /**
     * Intercepte les exceptions de type {@link DataIntegrityViolationException},
     * typiquement levées lors d'une violation de contrainte en base de données (ex: contrainte d'unicité ou clé étrangère).
     *
     * @param ex l'exception levée lorsqu'une opération de persistence viole une contrainte d'intégrité
     * @return une map contenant un message explicatif de l'erreur, avec un code HTTP 409 (CONFLICT)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // Code de retour
    @ResponseBody // Assure que la réponse est envoyée au format JSON (dans le corp de la réponse)
    public Map<String, Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {

        ConstraintViolationException cause = (ConstraintViolationException) ex.getCause();
        if (cause.getKind() == ConstraintViolationException.ConstraintKind.UNIQUE) {
            return Map.of("message", "Le champs doit contenir des valeurs uniques");
        } else {
            return Map.of("message", "Une contrainte de clé étrangère a été violée");
        }
    }

    //à décommenter uniqyement pour pouvoir voir les erreurs en production/staging en l'absence de console

    /**
     * Intercepteur global pour toutes les autres exceptions non spécifiquement traitées.
     * (Méthode commentée par défaut — à activer si besoin d'un retour détaillé en production/staging)
     *
     * @param ex l'exception inattendue levée à l'exécution
     * @return une map contenant la trace complète de l'erreur, avec un code HTTP 500 (INTERNAL_SERVER_ERROR)
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Code de retour
//    @ResponseBody
//    protected Map<String, Object> handleAllError(RuntimeException ex) {
//
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        ex.printStackTrace(pw);
//        String bodyOfResponse = sw.toString();
//
//        return Map.of("message", bodyOfResponse);
//    }
}
