package com.senai.crud_spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImagemService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file) throws IOException {
        // Gerar um nome único para a foto
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Definir o caminho completo do arquivo
        Path targetLocation = Paths.get(uploadDir).toAbsolutePath().normalize().resolve(fileName);

        // Criar diretório se não existir
        Files.createDirectories(targetLocation.getParent());

        // Copiar o arquivo para o local de destino (sobrescrever se já existir)
        Files.copy(file.getInputStream(), targetLocation);

        // Retornar o nome do arquivo
        return targetLocation.toString();
    }

    public Path loadFile(String fileName) {
        return Paths.get(uploadDir).resolve(fileName).normalize();
    }
}
