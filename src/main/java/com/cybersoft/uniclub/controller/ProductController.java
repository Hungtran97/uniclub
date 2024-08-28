package com.cybersoft.uniclub.controller;

import com.cybersoft.uniclub.exception.FileException;
import com.cybersoft.uniclub.request.AddProductRequest;
import com.cybersoft.uniclub.response.BaseResponse;
import com.cybersoft.uniclub.service.FilesStorageService;
import com.cybersoft.uniclub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProduct(){
        BaseResponse response = new BaseResponse();
        response.setData(productService.getProducts());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addProducts (AddProductRequest request){
        BaseResponse response = new BaseResponse();
        productService.addProduct(request);
        return new ResponseEntity<>("Hello add product", HttpStatus.OK);
    }
}
