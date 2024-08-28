package com.cybersoft.uniclub.service;

import com.cybersoft.uniclub.dto.ProductDTO;
import com.cybersoft.uniclub.request.AddProductRequest;

import java.util.List;

public interface ProductService {
    void addProduct(AddProductRequest request);

    List<ProductDTO> getProducts ();

}
