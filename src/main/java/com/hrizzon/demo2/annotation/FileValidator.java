package com.hrizzon.demo2.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;


public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private List<String> acceptedTypes;
    private long maxSize;
    private final FileConfig fileConfig;

    @Autowired
    public FileValidator(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        String[] customTypes = constraintAnnotation.acceptedTypes();
        if (customTypes.length > 0) {
            this.acceptedTypes = Arrays.asList(customTypes);
        } else {
            this.acceptedTypes = Arrays.asList(fileConfig.getDefaultAcceptedTypes());
        }
        this.maxSize = constraintAnnotation.maxSize();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            return true; // Si le fichier est facultatif ou vide
        }

        // Vérification du type de contenu et de la taille
        return FileValidationUtil.isValidFileSize(file, maxSize, context) &&
                FileValidationUtil.isValidFileType(file, acceptedTypes, context);
    }
}