package com.hrizzon.demo2.annotation;

import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class FileValidationUtil {

    public static boolean isValidFileType(MultipartFile file, List<String> acceptedTypes, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Si le fichier est facultatif ou vide
        }
        String contentType = file.getContentType();
        if (contentType == null || !acceptedTypes.contains(contentType)) {
            context.buildConstraintViolationWithTemplate(
                            "Extension de fichier invalide : '" + contentType + "'. acceptés : '" +
                                    String.join("', '", acceptedTypes) + "'")
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }

    public static boolean isValidFileSize(MultipartFile file, long maxSize, ConstraintValidatorContext context) {
        if (maxSize > 0 && file.getSize() > maxSize) {
            context.buildConstraintViolationWithTemplate("Taille de fichier trop grande : " + file.getSize() + ", max : " + maxSize)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
            return false;
        }
        return true;
    }

}
