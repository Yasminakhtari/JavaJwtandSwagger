package com.example.demo.controller;

import com.example.demo.entity.UserFile;
import com.example.demo.service.FileService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @PostMapping
            (consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, //Its used to view choose file button in swagger Ui
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> userFileUpload(
            @RequestParam String fileName,
            @RequestParam("file") MultipartFile file
            ){
        UserFile newFile=userService.save(fileName,file);
        return ResponseEntity.ok(newFile);
    }
    @GetMapping("/download/{type}/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String type, @PathVariable String filename) {
        Resource resource = fileService.loadFileAsResource(filename, type);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
