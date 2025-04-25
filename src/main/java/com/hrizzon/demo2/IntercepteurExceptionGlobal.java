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

@ControllerAdvice
public class IntercepteurExceptionGlobal {

    // But ici est d'intercepter des erreurs

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
