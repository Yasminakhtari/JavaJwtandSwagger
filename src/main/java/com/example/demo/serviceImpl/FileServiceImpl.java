package com.example.demo.serviceImpl;

import com.example.demo.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Service
public class FileServiceImpl implements FileService {
    private final Path rootLocation = Paths.get("uploads");

    @Override
    public String storeFile(MultipartFile file, String type) {
        try {
            Path uploadPath = rootLocation.resolve(type);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path destinationFile = uploadPath.resolve(
                    Paths.get(file.getOriginalFilename())).normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(uploadPath.toAbsolutePath())) {
                // This is a security check
                return "Cannot store file outside current directory.";
            }

            try (var inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }

            return  file.getOriginalFilename();
        } catch (IOException e) {
            return "Failed to upload file.";
        }
    }

    @Override
    public Resource loadFileAsResource(String filename, String type) {
        try {
            Path file = rootLocation.resolve(type).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
