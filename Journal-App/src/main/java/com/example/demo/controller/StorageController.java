package com.example.demo.controller;

import com.example.demo.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class StorageController {
    @Autowired
    private StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file) throws IOException {
        String uploadImage = service.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = service.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/jpg"))
                .body(imageData);
    }
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteImage(@PathVariable String fileName) {
        boolean isDeleted = service.deleteImage(fileName);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("Image deleted successfully: " + fileName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found: " + fileName);
        }
    }
}
