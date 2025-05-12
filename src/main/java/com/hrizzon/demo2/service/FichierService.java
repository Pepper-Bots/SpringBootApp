package com.hrizzon.demo2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FichierService {

    @Value("${public.upload.folder}")
    private String publicUploadFolder;

    public void uploadToLocalFileSystem(MultipartFile fichier, String fileName) throws IOException {
        uploadToLocalFileSystem(fichier.getInputStream(), fileName);
    }

    public void uploadToLocalFileSystem(MultipartFile fichier, String fileName) throws IOException {

        Path storageDirectory = Paths.get(publicUploadFolder);

        if (!Files.exists(storageDirectory)) {
            try {
                Files.createDirectories(storageDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Path destination = Paths.get(storageDirectory.toString() + "/" + fileName);

        Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);

    }
}
