package com.cybersoft.uniclub.controller;

import com.cybersoft.uniclub.exception.FileException;
import com.cybersoft.uniclub.response.BaseResponse;
import com.cybersoft.uniclub.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    FilesStorageService storageService;
    @PostMapping
    public ResponseEntity<?> addProducts (@RequestParam("file") MultipartFile file){
        BaseResponse response = new BaseResponse();
        try {
            storageService.save(file);
            response.setMessage("Uploaded the file successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
            throw new FileException("Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage());

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
