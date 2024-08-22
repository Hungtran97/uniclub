package com.cybersoft.uniclub.service.imp;

import com.cybersoft.uniclub.entity.*;
import com.cybersoft.uniclub.repository.ProductRepository;
import com.cybersoft.uniclub.repository.VariantRepository;
import com.cybersoft.uniclub.request.AddProductRequest;
import com.cybersoft.uniclub.service.FilesStorageService;
import com.cybersoft.uniclub.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private FilesStorageService filesStorageService;

    @Transactional
    @Override
    public void addProduct(AddProductRequest request) {
        ProductEntity product = new ProductEntity();
        product.setName(request.name());
        product.setDesc(request.desc());
        product.setInfo(request.information());
        product.setPrice(request.price());

        SizeEntity size = new SizeEntity();
        size.setId(request.idSize());

        ColorEntity color = new ColorEntity();
        color.setId(request.idColor());

        BrandEntity brand = new BrandEntity();
        brand.setId(request.idBrand());

        product.setBrand(brand);
        product.setColor(color);
        product.setSize(size);

        ProductEntity productSave = productRepository.save(product);

        VariantEntity variant = new VariantEntity();
        variant.setProduct(productSave);

        variantRepository.save(variant);

        filesStorageService.save(request.file());

    }
}
