package com.cybersoft.uniclub.controller;

import com.cybersoft.uniclub.exception.FileException;
import com.cybersoft.uniclub.request.AddProductRequest;
import com.cybersoft.uniclub.response.BaseResponse;
import com.cybersoft.uniclub.service.FilesStorageService;
import com.cybersoft.uniclub.service.ProductService;
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
    private ProductService productService;
    @PostMapping
    public ResponseEntity<?> addProducts (AddProductRequest request){
        BaseResponse response = new BaseResponse();
        productService.addProduct(request);
        return new ResponseEntity<>("Hello add product", HttpStatus.OK);
    }
}
