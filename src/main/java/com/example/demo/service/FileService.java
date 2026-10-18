package com.example.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
// changes
    String storeFile(MultipartFile file, String type);

    Resource loadFileAsResource(String filename, String type);
}
