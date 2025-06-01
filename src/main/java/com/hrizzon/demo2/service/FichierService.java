package com.hrizzon.demo2.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Service utilitaire pour la gestion des fichiers (upload, récupération).
 * <p>
 * Ce service permet de :
 * <ul>
 *     <li>Uploader des fichiers (public ou privé) sur le système de fichiers local.</li>
 *     <li>Récupérer une image par son nom sous forme de tableau d'octets.</li>
 * </ul>
 * Les chemins d'upload sont configurables via les propriétés {@code public.upload.folder} et {@code private.upload.folder}.
 *
 * @author [TonNom]
 */
@Service
public class FichierService {

    /**
     * Dossier de stockage pour les fichiers publics, injecté depuis le fichier de configuration.
     */
    @Value("${public.upload.folder}")
    private String publicUploadFolder;

    /**
     * Dossier de stockage pour les fichiers privés, injecté depuis le fichier de configuration.
     */
    @Value("${private.upload.folder}")
    private String privateUploadFolder;

    /**
     * Upload un fichier dans le système local à partir d'un {@link MultipartFile}.
     *
     * @param fichier    Le fichier à uploader (provenant d'un formulaire ou d'une requête HTTP).
     * @param fileName   Le nom sous lequel le fichier sera stocké.
     * @param publicFile True pour stocker dans le dossier public, false pour le dossier privé.
     * @throws IOException Si une erreur survient lors de la lecture ou de l'écriture du fichier.
     */
    public void uploadToLocalFileSystem(MultipartFile fichier, String fileName, boolean publicFile) throws IOException {
        uploadToLocalFileSystem(fichier.getInputStream(), fileName, publicFile);
    }

    /**
     * Upload un fichier dans le système local à partir d'un flux d'entrée.
     *
     * @param inputStream Flux d'entrée du fichier à stocker.
     * @param fileName    Le nom du fichier une fois stocké.
     * @param publicFile  True pour stockage dans le dossier public, false pour privé.
     * @throws IOException Si une erreur survient lors de l'écriture du fichier.
     */
    public void uploadToLocalFileSystem(InputStream inputStream, String fileName, boolean publicFile) throws IOException {

        Path storageDirectory = Paths.get(publicFile ? publicUploadFolder : privateUploadFolder);

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

    /**
     * Récupère une image stockée dans le dossier privé à partir de son nom.
     *
     * @param nomImage Le nom du fichier image à récupérer.
     * @return Le contenu du fichier sous forme de tableau d'octets.
     * @throws FileNotFoundException Si le fichier n'existe pas ou n'est pas accessible.
     */
    public byte[] getImageByName(String nomImage) throws FileNotFoundException {

        Path destination = Paths.get(privateUploadFolder + "/" + nomImage);// retrieve the image by its name

        try {
            return IOUtils.toByteArray(destination.toUri());
        } catch (IOException e) {
            throw new FileNotFoundException(e.getMessage());
        }

    }
}
